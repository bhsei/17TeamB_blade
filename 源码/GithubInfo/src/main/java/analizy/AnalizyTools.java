package analizy;

import meta.model.CommitObject;
import meta.model.author;
import meta.model.commit;
import model.ClassificationEntity;
import model.RepoEntity;
import org.apache.poi.hssf.usermodel.*;
import repository.ClassificationRepository;
import model.CommitEntity;
import model.UserEntity;
import repository.CommitRepository;
import repository.UserRepository;
import utils.DateUtil;
import utils.ExcelUtil;
import utils.PathUtil;
import utils.ProgressUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created this one by HMH on 2017/4/18.
 */
public class AnalizyTools {

    public void analizyClass(List<CommitObject> commitObjectList, RepoEntity repoEntity) throws IOException, ParseException {

        //生成类别列表
        List<ClassificationEntity> classificationEntityList = ClassificationRepository.getAllKind();

        for (int i = 0; i < commitObjectList.size(); i++) {
            CommitObject cur = commitObjectList.get(i);
            //取出数据
            author author = cur.getCommit().getAuthor();
            commit commit = cur.getCommit();
            //填充有效数据
            UserEntity user = UserRepository.getUser(author);
            if (user == null) {
                user = new UserEntity();
                user.setName(author.getName());
                user.setEmail(author.getEmail());
            }
            //新增提交
            CommitEntity commitEntity = new CommitEntity();
            commitEntity.setUser(user);
            commitEntity.setDate(commit.getAuthor().getDate());
            commitEntity.setMessage(commit.getMessage().replace("\n","").replace("\r","").trim());
            CommitRepository.add(commitEntity);
            //更新用户的提交记录
            user.getCommitEntityList().add(commitEntity);
            UserRepository.updateUser(user);
            //将提交分类
            List<ClassificationEntity> classificationEntities = ClassificationRepository.getAllKind();
            for (ClassificationEntity cfe : classificationEntities) {
                if (commitEntity.getMessage().toUpperCase().contains(cfe.getName().toUpperCase())) {
                    //添加到分类中
                    cfe.getCommitEntityList().add(commitEntity);
                    commitEntity.addClass(cfe);
                }
            }
            repoEntity.addcommit(commitEntity);
            repoEntity.updateUser(user);
        }
        //输出
        print(repoEntity);
    }


    public void print(RepoEntity repoEntity) throws IOException {

        //创建Excel文档
        HSSFWorkbook excelResult = ExcelUtil.createExcel();

        printAllUser(excelResult, repoEntity);
        printAllCommit(excelResult, repoEntity);
        printAllKind(excelResult, repoEntity);
        printUserDetail(excelResult, repoEntity);

        //存储Excel文档
        ExcelUtil.saveToFile(excelResult, PathUtil.getDesktopPath() + "/commitB.xls");

    }

    //输出所有的提交记录
    public void printAllCommit(HSSFWorkbook excelResult,RepoEntity repoEntity) {

        HSSFSheet allCommitSheet = ExcelUtil.createSheet(excelResult,"所有提交","提交时间", "提交人", "提交注释");

        for (int i=0;i<repoEntity.getCommitEntityList().size();i++) {
            CommitEntity ce = repoEntity.getCommitEntityList().get(i);
            ExcelUtil.addRow(allCommitSheet,i+1,false,ce.getDateStr(),ce.getUser().getName(),ce.getMessage());
            System.out.println(ce);
        }
        //创建时间变化表
        addTimeSplitSheet(excelResult, "总提交", repoEntity.getCommitEntityList());

        System.out.println();

    }

    //输出用户信息
    public void printAllUser(HSSFWorkbook excelResult,RepoEntity repoEntity) {
        System.out.println(" ===> 用户信息");
        HSSFSheet userSheet = ExcelUtil.createSheet(excelResult,"用户信息");
        ExcelUtil.SetHeaders(userSheet, "用户名", "邮箱", "提交次数");

        for(int i=0;i<repoEntity.getUserEntityList().size();i++) {
            UserEntity userEntity = repoEntity.getUserEntityList().get(i);
            ExcelUtil.addRow(userSheet,i+1,false,userEntity.getName(),userEntity.getEmail(),userEntity.getCommitEntityList().size());
            System.out.println(repoEntity.getUserEntityList().get(i));
        }

        System.out.println();
        System.out.println(" ===> 提交结果");
    }

    //输出关键词分类
    public void printAllKind(HSSFWorkbook excelResult,RepoEntity repoEntity) {
        System.out.println(" ===> 关键词分类");

        HSSFSheet kindProgressSheet = ExcelUtil.createSheet(excelResult,"分类提交总览","类别", "提交次数", "百分比");

        List<ClassificationEntity> classificationEntities = ClassificationRepository.getAllKind();
        for (int cfeIndex = 0; cfeIndex < classificationEntities.size(); cfeIndex++) {

            ClassificationEntity cfe = classificationEntities.get(cfeIndex);

            if (cfe.getCommitEntityList().size() == 0) {
                continue;
            }
            HSSFSheet kindCommitSheet = ExcelUtil.createSheet(excelResult, "分类" + cfe.getName(), "提交时间", "提交人", "提交注释");
            System.out.println(" ======> 关键词【" + cfe.getName() + "】");
            for (int i = 0; i < cfe.getCommitEntityList().size(); i++) {
                CommitEntity ce = cfe.getCommitEntityList().get(i);
                ExcelUtil.addRow(kindCommitSheet, i + 1, false, ce.getDateStr(), ce.getUser().getName(), ce.getMessage());
                System.out.println(cfe.getCommitEntityList().get(i));
            }

            //创建时间变化表
            addTimeSplitSheet(excelResult, "分类"+cfe.getName(), repoEntity.getCommitEntityList());

            //计算进度条
            int progress = 100 * cfe.getCommitEntityList().size() / repoEntity.getCommitEntityList().size();
            System.out.println(" ======> 关键词下提交数量：" + cfe.getCommitEntityList().size() + " ， 占总数百分比 :" + progress);
            progress = progress > 1 ? progress : 1;
            ProgressUtil.show(Math.round(progress));

            ExcelUtil.addRow(kindProgressSheet, cfeIndex+1,false,cfe.getName(),cfe.getCommitEntityList().size(),progress);

            System.out.println();
            System.out.println();
        }
    }

    public void printUserDetail(HSSFWorkbook excelResult,RepoEntity repoEntity) {
        HSSFSheet userProgressSheet = ExcelUtil.createSheet(excelResult, "用户提交总览", "用户", "提交次数", "百分比");

        System.out.println(" ===> 个人提交统计");
        List<UserEntity> userEntityList = UserRepository.getAll();
        for (int userIndex = 0; userIndex < userEntityList.size(); userIndex++) {

            UserEntity uc = userEntityList.get(userIndex);
            HSSFSheet userDetailSheet = ExcelUtil.createSheet(excelResult, "用户 " + uc.getName(), "提交时间", "提交人", "提交注释");

            System.out.println(" ======> 用户【" + uc.getName() + "】提交统计");
            for (int i = 0; i < uc.getCommitEntityList().size(); i++) {
                CommitEntity ce = uc.getCommitEntityList().get(i);
                ExcelUtil.addRow(userDetailSheet, i + 1, false, ce.getDateStr(), ce.getUser().getName(), ce.getMessage());
                System.out.println(uc.getCommitEntityList().get(i));
            }

            //创建时间变化表
            addTimeSplitSheet(excelResult, "用户" + uc.getName(), uc.getCommitEntityList());

            double progress = 100.0 * uc.getCommitEntityList().size() / repoEntity.getCommitEntityList().size();
            progress = progress > 1 ? progress : 1;
            System.out.println(" ======> 用户提交数：" + uc.getCommitEntityList().size() + " ， 占总数百分比: " + progress);
            ProgressUtil.show(Math.round(progress));
            ExcelUtil.addRow(userProgressSheet, userIndex + 1, false, uc.getName(), uc.getCommitEntityList().size(), progress);

            System.out.println();
            System.out.println();
        }
    }

    public void addTimeSplitSheet(HSSFWorkbook excel,String sheetName, List<CommitEntity> commitEntityList) {
        HSSFSheet weekSheet = ExcelUtil.createSheet(excel, sheetName+"周报", "周数", "次数");
        HSSFSheet daySheet = ExcelUtil.createSheet(excel, sheetName + "日报", "天", "次数");
        HSSFSheet dddSheet = ExcelUtil.createSheet(excel, sheetName + "星期分布", "周几", "次数");
        HashMap<Integer, Integer> weekFilter = new HashMap<>();
        HashMap<Integer, Integer> dayFilter = new HashMap<>();
        HashMap<Integer, Integer> dddFilter = new HashMap<>();
        for(int i=0;i<commitEntityList.size();i++) {
            int weekNums= DateUtil.getWeekOfYear(commitEntityList.get(i).getDate());
            weekFilter.put(weekNums,weekFilter.getOrDefault(weekNums,0)+1);
        }
        for(int i=0;i<commitEntityList.size();i++) {
            int weekNums= DateUtil.getDayOfYear(commitEntityList.get(i).getDate());
            dayFilter.put(weekNums,dayFilter.getOrDefault(weekNums,0)+1);
        }
        for(int i=0;i<commitEntityList.size();i++) {
            int dddNums= DateUtil.getDayOfWeek(commitEntityList.get(i).getDate());
            dddNums=(dddNums-1)==0?7:(dddNums-1);
            dddFilter.put(dddNums,dddFilter.getOrDefault(dddNums,0)+1);
        }
        int i=1;
        for (Map.Entry<Integer, Integer> entity : weekFilter.entrySet()) {
            ExcelUtil.addRow(weekSheet,i++,false,entity.getKey(),entity.getValue());
        }
        i = 1;
        for (Map.Entry<Integer, Integer> entity : dayFilter.entrySet()) {
            ExcelUtil.addRow(daySheet,i++,false,entity.getKey(),entity.getValue());
        }
        i = 1;
        for (Map.Entry<Integer, Integer> entity : dddFilter.entrySet()) {
            ExcelUtil.addRow(dddSheet,i++,false,entity.getKey(),entity.getValue());
        }
    }
}
