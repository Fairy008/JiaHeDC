package com.jh.manage.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.manage.loader.Enum.LoaderEnum;
import com.jh.manage.storage.entity.DataStatic;
import com.jh.manage.storage.mapping.IDataStaticMapper;
import com.jh.manage.storage.model.ImportStaticParam;
import com.jh.manage.storage.service.IDataStaticService;
import com.jh.util.ceph.CephUtils;
import com.jh.util.ceph.LinkUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 1.矢量数据实现类
 *
 * @version <1> 2018-04-27 9:44 zhangshen: Created.
 */
@Service
@Transactional
public class DataStaticServiceImpl implements IDataStaticService{

    @Autowired
    private IDataStaticMapper dataStaticMapper;

    //创建LINK
    LinkUtil linkUtil = LinkUtil.getInstance();

    /**
     * 导入矢量数据
     * 1.修改文件夹名称
     * 2.往数据库写入信息
     * 3.复制文件到指定目录
     * @param importStaticParam
     * @return
     * @version <1> 2018-04-26 zhangshen:Created.
     */
    @Override
    public ResultMessage importStatic(ImportStaticParam importStaticParam){
        ResultMessage result = ResultMessage.fail();
        if(importStaticParam != null && StringUtils.isNotBlank(importStaticParam.getFileName())){
            //1.修改文件夹名称
            //操作Linux，将文件（夹）添加前缀
            String importFileName = CephUtils.setPrefix(CephUtils.getAbsolutePath("") , importStaticParam.getFileName() , CephUtils.getCephCollectionImportPrefix());
            if(importFileName != null){
                if(importStaticParam.getFileType() == 1) { //文件夹

                    String fileName = importStaticParam.getFileName();
                    String fileNameStr = importStaticParam.getRegionCode() + File.separator + fileName.substring(fileName.lastIndexOf("\\")+1);// regionCode\文件夹名
                    String makeDir = CephUtils.makeStaticDirectory(fileNameStr);//  mnt\data\static\shapefile\regionCode\test
                    String oldPath = CephUtils.getAbsolutePath("") + importFileName;//原文件夹路径，如：\\192.168.1.210\mnt\module\collection\static\import_test
                    String newPath = CephUtils.getAbsolutePath("") + makeDir;//指定文件夹路径，如：\\192.168.1.210\mnt\data\static\shapefile\regionCode\test
                    /*//2.复制文件到指定目录
                    List<File> listFiles = CephUtils.copyFiles(oldPath, newPath, CephUtils.getDataStaticSuffixs());//复制.shp文件

                    //3.生成link
                    linkUtil.makeDataLink(listFiles, makeDir, importFileName);

                    //4.往数据库写入信息
                    //File[] files = CephUtils.getFilesContent(oldPath, CephUtils.getDataStaticSuffixs());//获取路径下的文件
                    if(listFiles != null && listFiles.size() > 0) {
                        List<DataStatic> list = new ArrayList<DataStatic>();
                        for (File f : listFiles) {
                            if (!f.isDirectory()) {//如果不是文件夹，插入信息
                                DataStatic dataStatic = new DataStatic();
                                dataStatic.setCreator(importStaticParam.getDataStatic().getCreator());
                                dataStatic.setCreatorName(importStaticParam.getDataStatic().getCreatorName());
                                dataStatic.setRegionId(importStaticParam.getDataStatic().getRegionId());//区域id
                                dataStatic.setWords(importStaticParam.getDataStatic().getWords());//关键字
                                //文件大小
                                try {
                                    int size = new FileInputStream(f).available();
                                    String fileSize = CephUtils.compileSizeUnit(size);
                                    dataStatic.setFileSize(fileSize);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //文件路径
                                String filePath = makeDir + File.separator + f.getName();// 文件路径\文件名
                                dataStatic.setFilePath(filePath);
                                list.add(dataStatic);
                            }
                        }
                        dataStaticMapper.insertDataStatic(list);
                    }*/

                    List<File> listFile = CephUtils.listAllFilesForDG(oldPath, CephUtils.getDataStaticSuffixs());//
                    List<DataStatic> list = new ArrayList<DataStatic>();
                    for(File f: listFile){
                        //4.分装数据
                        DataStatic dataStatic = new DataStatic();
                        dataStatic.setCreator(importStaticParam.getDataStatic().getCreator());
                        dataStatic.setCreatorName(importStaticParam.getDataStatic().getCreatorName());
                        dataStatic.setRegionId(importStaticParam.getDataStatic().getRegionId());//区域id
                        dataStatic.setWords(importStaticParam.getDataStatic().getWords());//关键字

                        String fileSize = CephUtils.compileSizeUnit(CephUtils.getFileSize(f));
                        dataStatic.setFileSize(fileSize);
                        //文件路径
                        String filePath = makeDir + File.separator + f.getName();// 文件路径\文件名
                        dataStatic.setFilePath(filePath);

                        File newFile = new File(newPath + File.separator + f.getName());//新建文件
                        if(newFile.exists()){ //删除目标文件夹的文件
                            newFile.delete();
                        }
                        boolean bool = f.renameTo(newFile);
                        if (bool){
                            //3.生成link
                            linkUtil.makeDataLinkForFile(f, makeDir, importFileName);

                            list.add(dataStatic);
                        }
                    }
                    if(null != list && list.size() > 0){
                        dataStaticMapper.insertDataStatic(list);
                    }
                }
                result = ResultMessage.success();
            }else{
                result = ResultMessage.fail(LoaderEnum.DATA_LOADER_PREFIX_ERROR.getValue(), LoaderEnum.DATA_LOADER_PREFIX_ERROR.getMessage());
            }
        }else{
            result = ResultMessage.fail(LoaderEnum.DATA_LOADER_STORAGE_PATH_NULL.getValue(), LoaderEnum.DATA_LOADER_STORAGE_PATH_NULL.getMessage());
        }
        return result;
    }

    /**
     * 矢量数据查询
     * @param importStaticParam
     * @return
     * @version <1> 2018-04-28 zhangshen:Created.
     */
    @Override
    public PageInfo<DataStatic> findDateStaticByPage(ImportStaticParam importStaticParam){
        PageHelper.startPage(importStaticParam.getPage(), importStaticParam.getRows());
        List<DataStatic> list = dataStaticMapper.findByPage(importStaticParam);
        return new PageInfo<DataStatic>(list);
    }

    /**
     * Description: 根据staticId,查询矢量数据
     * @param staticId
     * @return
     * @version <1> 2018/5/24 16:25 zhangshen: Created.
     */
    @Override
    public DataStatic findDateStaticById(Integer staticId){
        return dataStaticMapper.findDateStaticById(staticId);
    }
}
