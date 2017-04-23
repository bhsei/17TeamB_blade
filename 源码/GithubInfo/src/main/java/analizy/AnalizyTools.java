package analizy;

import meta.model.CommitObject;
import meta.model.author;
import meta.model.commit;
import model.ClassificationEntity;
import model.RepoEntity;
import repository.ClassificationRepository;
import model.CommitEntity;
import model.UserEntity;
import repository.CommitRepository;
import repository.UserRepository;
import utils.ProgressUtil;

import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class AnalizyTools {

    public void analizyClass(List<CommitObject> commitObjectList, RepoEntity repoEntity) {
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

            //更新用户
            user.getCommitEntityList().add(commitEntity);
            UserRepository.updateUser(user);


            //分类
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

        print(repoEntity);
    }

    public void print(RepoEntity repoEntity) {
        System.out.println(" ===> 用户信息");
        for(int i=0;i<repoEntity.getUserEntityList().size();i++) {
            System.out.println(repoEntity.getUserEntityList().get(i));
        }

        System.out.println();
        System.out.println(" ===> 提交结果");

        for (CommitEntity ce : repoEntity.getCommitEntityList()) {
            System.out.println(ce);
        }

        System.out.println();
        System.out.println(" ===> 关键词分类");

        List<ClassificationEntity> classificationEntities = ClassificationRepository.getAllKind();
        for (ClassificationEntity cfe : classificationEntities) {
            if(cfe.getCommitEntityList().size()==0)
                continue;
            System.out.println(" ======> 关键词【"+cfe.getName()+"】");
            for(int i=0;i<cfe.getCommitEntityList().size();i++) {
                System.out.println(cfe.getCommitEntityList().get(i));
            }
            int progress = 100 * cfe.getCommitEntityList().size() / repoEntity.getCommitEntityList().size();
            System.out.println(" ======> 关键词下提交数量："+cfe.getCommitEntityList().size()+" ， 占总数百分比 :"+progress);
            progress = progress > 1 ? progress : 1;
            ProgressUtil.show(Math.round(progress));
            System.out.println();
            System.out.println();
        }

        System.out.println(" ===> 个人提交统计");
        List<UserEntity> userEntityList = UserRepository.getAll();
        for (UserEntity user : userEntityList) {
            System.out.println(" ======> 用户【"+user.getName()+"】提交统计");
            for(int i=0;i<user.getCommitEntityList().size();i++) {
                System.out.println(user.getCommitEntityList().get(i));
            }
            double progress = 100.0 * user.getCommitEntityList().size() / repoEntity.getCommitEntityList().size();
            progress = progress > 1 ? progress : 1;
            System.out.println(" ======> 用户提交数："+user.getCommitEntityList().size()+ " ， 占总数百分比: "+progress);
            ProgressUtil.show(Math.round(progress));
            System.out.println();
            System.out.println();
        }

    }
}
