package com.ruoyi.common.utils.poi;
import cn.afterturn.easypoi.excel.ExcelExportUtil;

import cn.afterturn.easypoi.excel.ExcelImportUtil;

import cn.afterturn.easypoi.excel.entity.ExportParams;

import cn.afterturn.easypoi.excel.entity.ImportParams;

import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ruoyi.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.net.URLEncoder;

import java.util.List;

import java.util.Map;
import java.util.NoSuchElementException;



/**

 * @Author: JCccc

 * @Description:

 * @Date: 1/1/1 1:11

 */

public class MyExcelUtils {

//    /**
//
//     * 功能描述：复杂导出Excel，包括文件名以及表名,不创建表头
//
//     *
//
//     * @param list 导出的实体类
//
//     * @param title 表头名称
//
//     * @param sheetName sheet表名
//
//     * @param pojoClass 映射的实体类
//
//     * @param fileName
//
//     * @param response
//
//     * @return
//
//     */
//
//    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,
//
//                                   String fileName, HttpServletResponse response) {
//
//        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
//
//    }


//
//    /**
//
//     * 功能描述：默认导出方法
//
//     *
//
//     * @param list 导出的实体集合
//
//     * @param fileName 导出的文件名
//
//     * @param pojoClass pojo实体
//
//     * @param exportParams ExportParams封装实体
//
//     * @param response
//
//     * @return
//
//     */
//
//    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName,
//
//                                      HttpServletResponse response, ExportParams exportParams) {
//
//        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
//
//        if (workbook != null) {
//
//            downLoadExcel(fileName, response, workbook);
//
//        }
//
//    }


//
//    /**
//
//     * 功能描述：Excel导出
//
//     *
//
//     * @param fileName 文件名称
//
//     * @param response
//
//     * @param workbook Excel对象
//
//     * @return
//
//     */
//
//    private static void downLoadExcel(String fileName, HttpServletResponse response,
//
//                                      Workbook workbook) {
//
//        try {
//
//            response.setCharacterEncoding("UTF-8");
//
//            response.setHeader("content-Type", "multipart/form-data");
//
//            response.setHeader("Content-Disposition",
//
//                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//
//            workbook.write(response.getOutputStream());
//
//
//
//        } catch (IOException e) {
//
//            throw new RuntimeException(e);
//
//        }
//
//    }
//
//
//

//
//    /**
//
//     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
//
//     *
//
//     * @param file 上传的文件
//
//     * @param titleRows 表标题的行数
//
//     * @param headerRows 表头行数
//
//     * @param pojoClass Excel实体类
//
//     * @return
//
//     */
//
//    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
//
//                                          Class<T> pojoClass) {
//
//        if (file == null) {
//
//            return null;
//
//        }
//
//        ImportParams params = new ImportParams();
//
//        params.setTitleRows(titleRows);
//
//        params.setHeadRows(headerRows);
//
//        List<T> list = null;
//
//        try {
//
//            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
//
//        } catch (NoSuchElementException e) {
//
//            throw new RuntimeException("excel文件不能为空");
//
//        } catch (Exception e) {
//
//            throw new RuntimeException(e.getMessage());
//
//
//
//        }
//
//        return list;
//
//    }


    /**
     * excel 导出
     *
     * @param list     数据列表
     * @param fileName 导出时的excel名称
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
        defaultExport(list, fileName, response);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list     数据列表
     * @param fileName 导出时的excel名称
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
        //把数据添加到excel表格中
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * excel 导出
     *
     * @param list         数据列表
     * @param pojoClass    pojo类型
     * @param fileName     导出时的excel名称
     * @param response
     * @param exportParams 导出参数（标题、sheet名称、是否创建表头，表格类型）
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws IOException {
        //把数据添加到excel表格中
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * excel 导出
     *
     * @param list         数据列表
     * @param pojoClass    pojo类型
     * @param fileName     导出时的excel名称
     * @param exportParams 导出参数（标题、sheet名称、是否创建表头，表格类型）
     * @param response
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams, HttpServletResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * excel 导出
     *
     * @param list      数据列表
     * @param title     表格内数据标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  导出时的excel名称
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
    }



    /**
     * excel 导出
     *
     * @param list           数据列表
     * @param title          表格内数据标题
     * @param sheetName      sheet名称
     * @param pojoClass      pojo类型
     * @param fileName       导出时的excel名称
     * @param isCreateHeader 是否创建表头
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }


    /**
     * excel下载
     *
     * @param fileName 下载时的文件名称
     * @param response
     * @param workbook excel数据
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }



    /**
     * excel 导入
     *
     * @param file      excel文件
     * @param pojoClass pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws IOException {
        return importExcel(file, 1, 1, pojoClass);
    }

    /**
     * excel 导入
     *
     * @param filePath   excel文件路径
     * @param titleRows  表格内数据标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedSave(true);
        params.setSaveUrl("/excel/");
        try {
            return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("模板不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }


    /**
     * excel 导入
     *
     * @param file       上传的文件
     * @param titleRows  表格内数据标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (file == null) {
            return null;
        }
        try {
            return importExcel(file.getInputStream(), titleRows, headerRows, pojoClass);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param inputStream 文件输入流
     * @param titleRows   表格内数据标题行
     * @param headerRows  表头行
     * @param pojoClass   pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setSaveUrl("/excel/");
        params.setNeedSave(true);
        try {
            return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("excel文件不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }


}
