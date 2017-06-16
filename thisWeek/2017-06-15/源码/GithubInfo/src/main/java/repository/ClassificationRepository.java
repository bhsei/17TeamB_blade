package repository;

import model.ClassificationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class ClassificationRepository {

    static List<ClassificationEntity> classificationEntities;

    static{
        classificationEntities = new ArrayList<>();
        classificationEntities.add(new ClassificationEntity("添加"));
        classificationEntities.add(new ClassificationEntity("更新"));
        classificationEntities.add(new ClassificationEntity("修改"));
        classificationEntities.add(new ClassificationEntity("提交"));
        classificationEntities.add(new ClassificationEntity("恢复"));
        classificationEntities.add(new ClassificationEntity("删除"));
        classificationEntities.add(new ClassificationEntity("update"));
        classificationEntities.add(new ClassificationEntity("change"));
        classificationEntities.add(new ClassificationEntity("conflict"));
        classificationEntities.add(new ClassificationEntity("test"));
        classificationEntities.add(new ClassificationEntity("merge"));
        classificationEntities.add(new ClassificationEntity("评审"));
        classificationEntities.add(new ClassificationEntity("需求规格说明书"));
        classificationEntities.add(new ClassificationEntity("参考资料"));
        classificationEntities.add(new ClassificationEntity("评价"));
        classificationEntities.add(new ClassificationEntity("课堂"));
        classificationEntities.add(new ClassificationEntity("readme"));
        classificationEntities.add(new ClassificationEntity("mpp"));
        classificationEntities.add(new ClassificationEntity("PPT"));
        classificationEntities.add(new ClassificationEntity("幻灯片"));
        classificationEntities.add(new ClassificationEntity("keynote"));
        classificationEntities.add(new ClassificationEntity("project"));
        classificationEntities.add(new ClassificationEntity("需求"));
        classificationEntities.add(new ClassificationEntity("错误"));
        classificationEntities.add(new ClassificationEntity("有效"));
        classificationEntities.add(new ClassificationEntity("计划"));
        classificationEntities.add(new ClassificationEntity("重要"));
        classificationEntities.add(new ClassificationEntity("微调"));
        classificationEntities.add(new ClassificationEntity("实验一"));
        classificationEntities.add(new ClassificationEntity("实验二"));
        classificationEntities.add(new ClassificationEntity("实验三"));
        classificationEntities.add(new ClassificationEntity("实验四"));
        classificationEntities.add(new ClassificationEntity("实验五"));
        classificationEntities.add(new ClassificationEntity("实验六"));
        classificationEntities.add(new ClassificationEntity("实验七"));
        classificationEntities.add(new ClassificationEntity("实验八"));
        classificationEntities.add(new ClassificationEntity("会议"));
    }

    public static List<ClassificationEntity> getAllKind(){
        return classificationEntities;
    }

    public static void clear() {
        for (ClassificationEntity entity : classificationEntities) {
            entity.clearCommit();
        }
    }

    public static void add(ClassificationEntity classification) {
        classificationEntities.add(classification);
    }

    public static void remove(ClassificationEntity classification) {
        classificationEntities.remove(classification);
    }

    public static ClassificationEntity getUser(String name) {
        for(int i=0;i<classificationEntities.size();i++) {
            if (classificationEntities.get(i).getName().equals(name)) {
                return classificationEntities.get(i);
            }
        }
        return null;
    }

    public static void updateUser(ClassificationEntity classificationEntity) {
        for(int i=0;i<classificationEntities.size();i++) {
            if (classificationEntities.get(i).getName().equals(classificationEntity.getName())) {
                classificationEntities.set(i,classificationEntity) ;
            }
        }
    }

    public static ClassificationEntity get(String name) {
        for (ClassificationEntity entity : classificationEntities) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public static void addClassification(String... objs) {
        for(int i=1;i<objs.length;i++) {
            if (get(objs[i]) == null) {
                add(new ClassificationEntity(objs[i]));
            }
        }
    }
}
