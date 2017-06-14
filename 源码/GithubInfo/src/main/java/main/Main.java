package main;

import analizy.AnalizyTools;
import com.alibaba.fastjson.JSON;
import meta.model.CommitObject;
import model.RepoEntity;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import repository.ClassificationRepository;
import utils.ExcelUtil;
import utils.PathUtil;
import utils.PropsUtil;
import utils.httpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        try {

            Properties properties = PropsUtil.loadProps("config.properties");
            String key = "hh23485";
            String value = (String) properties.get(key);
            String param = "?access_token=" + value;

            String repositoryPath;
            if (args.length > 0) {
                repositoryPath=args[0];
            }
            else{
                throw new Exception();
            }
            ClassificationRepository.addClassification(args);

            System.out.println(" ===> 尝试获取仓库【"+repositoryPath+"】的提交情况");
            String getCommits = "https://api.github.com/repos/"+repositoryPath+"/commits";
            List<String> returnJson = new ArrayList<>();
            String htmlReg = "<(.*?page=(.*?))>.*?<(.*?page=(.*?))>";
            String nextHtml;
            String lastHtml;
            int next = 0;
            int last = 0;

            System.out.println(" ===> 获取提交信息");

            List<CommitObject> commitObjectList = new ArrayList<>();
            getCommits = getCommits + param;
            int count=0;
            do {
                Response response1 = httpUtils.get(getCommits);
                List<String> headers = response1.headers("Link");
                String returnAns = headers.get(0);
                Pattern patternhtml = Pattern.compile(htmlReg);
                Matcher matchernext = patternhtml.matcher(returnAns);
                matchernext.find();
                nextHtml = matchernext.group(1);
                lastHtml = matchernext.group(3);
                next = Integer.parseInt(matchernext.group(2));
                last = Integer.parseInt(matchernext.group(4));
                System.out.println(" ===> 获取第"+(next-1)+"页");
                returnJson.add(response1.body().string());
                getCommits = nextHtml;
                count++;
                if(count==10000){
                    throw new Exception("错误的路径");
                }
            } while (next < last);


            Response response2 = httpUtils.get(getCommits);
            returnJson.add(response2.body().string());
            System.out.println(" ===> 获取第"+(next)+"页");

            for (int i = 0; i < returnJson.size(); i++) {
                String string = returnJson.get(i);
                List<CommitObject> tempObject = JSON.parseArray(string, CommitObject.class);
                for (int j = 0; j < tempObject.size(); j++) {
                    commitObjectList.add(tempObject.get(j));
                }
            }
            System.out.println(" ===> 获得总提交数"+commitObjectList.size());

            System.out.println();



            AnalizyTools analizyTools = new AnalizyTools();
            RepoEntity repoEntity = new RepoEntity();
            analizyTools.analizyClass(commitObjectList, repoEntity);



        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" ===> 参数或网络错误，如果参数错误，请注意参数格式：java -jar **.jar 仓库标题 [关键词] [关键词]，仓库标题格式如：bhsei/17TeamB_blade，关键词为可选参数，使用空格间隔");
        }

    }
}
