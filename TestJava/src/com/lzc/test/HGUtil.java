package com.lzc.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzc.model.StatMdPpmdDTO;

public class HGUtil {
	
	public static void test() {
		//readData();
		//readObject();
		//readField();
		//readTableData();		
		//readField();
		
		exportTable();
	}
	
	private static void exportTable() {
		
	}
	
    private void updateToStatMdPpmd(String menuCode,List<Map<String,Object>> listData) throws Exception {

        List<StatMdPpmdDTO> dtoList = new ArrayList<>();
        for(Map<String,Object> row : listData) {
        	StatMdPpmdDTO dto = new StatMdPpmdDTO();
        	dtoList.add(dto);
        	dto.setMenuCode(menuCode);
        	dto.setIsStandardization(getStringValue(row.get("isStandardization")));
        	dto.setIndexLineCoding(getStringValue(row.get("indexLineCoding")));
        	dto.setProject(getStringValue(row.get("project")));
        	dto.setUnit(getStringValue(row.get("unit")));
        	dto.setParentCode(getStringValue(row.get("parentCode")));
        	String temp=getStringValue(row.get("dataType"));
        	if(temp!=null && temp.length()>0) {
        		dto.setDataType(Integer.parseInt(temp));
        	}
        }

        //statMdPpmdService.freshUpdateList(dtoList);
    }
    
    private String getStringValue(Object obj) {
    	if(obj!=null) {
    		return obj.toString();
    	}    	
    	return null;
    }
    
    

	
    private static List<Map<String,Object>> readTableData()
    {
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\data.xlsx";
		List<Map<String,Object>> listTable=new ArrayList<>();		
		int i = 0;
		try {
			InputStream is = new FileInputStream(filePath);
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			XSSFSheet sheet = xwb.getSheetAt(0);
			String nowMenuCode = null;
			Map<String,Object> nowTable = null;
			int nMark = -1;
			for (i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				//System.out.print((i+1) + "行");		
				if(row == null || row.getLastCellNum()<0) {
					//System.out.println("");
					continue;
				}
				
				if(row.getLastCellNum()>0) {
					boolean bFind = false;
					if(row.getCell(0)!=null && "*table".equals(row.getCell(0).toString())) { nMark = 0; bFind=true; }
					if(row.getCell(1)!=null && "*table".equals(row.getCell(1).toString())) { nMark = 1; bFind=true; }
					if(nMark>=0 && bFind) {
						Map<String,Object> mapTable = new HashMap<>();
						String menuName = row.getCell(nMark+1).toString();
						String menuCode = row.getCell(nMark+2).toString();
						String gridCode = row.getCell(nMark+3).toString();
						//System.out.println((i+1) + "======"+menuCode + "::" + gridCode + "::" +menuName);
						mapTable.put("menuName", menuName);
						mapTable.put("menuCode", menuCode);
						mapTable.put("gridCode", gridCode);
						mapTable.put("listData",new ArrayList<Map<String,Object>>());
						
						i++;
						Row rowField = sheet.getRow(i);
						List<String> fields = new ArrayList<>();
						for (int j = 0; j < rowField.getLastCellNum(); j++) {
							if(rowField.getCell(j)==null) {continue;}
							String field=rowField.getCell(j).toString();
							if(field.trim().length()==0) {continue;}
							fields.add(field);
						}
						mapTable.put("fields",fields);
						mapTable.put("fieldsDisplay",getShowFields(menuCode));
						listTable.add(mapTable);
						nowMenuCode = menuCode;
						nowTable = mapTable;
						continue;						
					}
				}
				
				if(nowMenuCode==null) {
					continue;
				}
				
				//读取表数据
				List<String> fields = (List<String>)nowTable.get("fields");
				List<?> fieldsDisplay = (List)nowTable.get("fieldsDisplay");				
				List<Map<String,Object>> listData=(List)nowTable.get("listData");
				Map<String,Object> mapData = new HashMap<>();
				listData.add(mapData);				
				//System.out.println("===fields.size==="+fields.size()+"::["+row.getLastCellNum()+"]");				
				for(int z=0;z<fields.size();z++) {
					Cell cell = null;
					Object cellValue = null;
					if(z<=row.getLastCellNum()) { cell = row.getCell(z+nMark);}
					if(cell !=null ) {
						if(cell.getCellType()==CellType.FORMULA) {
							boolean bRead=false;
							try {
								cellValue = cell.getNumericCellValue();
								bRead=true;
							}catch(Exception e) {
								//e.printStackTrace();
							}
							if(!bRead) {
								try {
									cellValue = cell.getCellFormula();
									bRead=true;
								}catch(Exception e) {
									//e.printStackTrace();
								}							
							}
						}
						
						if(cellValue == null) {
							cellValue=cell.toString();
						}							
					}
					
					//System.out.println("=====now=="+z+"::"+fields.get(z)+"::"+cellValue);
					//mapData.put(fields.get(z), cellValue); //赋值	
					
					String column=null;
					for(Object obj : fieldsDisplay) {
						Map mb=(Map)obj;
						if(fields.get(z).equals(mb.get("name")) && mb.get("column")!=null) {
							column=(String)mb.get("column");
							break;
						}
					}
					if(column!=null) {
						mapData.put(column, cellValue); //赋值
					}
				}
			}
		} catch (Exception e) {
			System.out.println("读取到第"+(i+1)+"行出现异常:" + e);
			e.printStackTrace();
		}
		
		/****
		for(int k=0;k<listTable.size();k++) {
			if(k>0) {break;}
			Map<String,Object> table = listTable.get(k);
			System.out.println(table);
		}
		/***/
		//listTable.clear();
		
		return listTable;
    }
	
    
    private static void readField()
    {
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\tmp3.xlsx";
		List<String> list=new ArrayList<>();
		String str="";
    	//Object obj=null;
		try {
			InputStream is = new FileInputStream(filePath);
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			Row row = null;
			String cell = null;
			// 循环输出表格中的内容
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				str="";
				row = sheet.getRow(i);
				//System.out.print((i+1) + "行");				
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if(row.getCell(j)==null) {continue;}
					cell = row.getCell(j).toString();
					//System.out.print(j+"["+cell + "]\t");
					if("字段".equals(cell)) {
						str=",";
						continue;
					}
					if(str.length()>0) {
						str=str + "," + cell;
					}
				}				
				//System.out.println("");
				if(str.length()>0) {list.add(str);}
			}
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
			e.printStackTrace();
		}
		
		String out="";
		for(String s : list) {
			s=s.substring(2);
			String[] arr=s.split(",");
			out="[";
			for(int i=0;i<arr.length;i++) {
				if(i>0) {out = out +",";}
				out=out + "{\"name\":\""+arr[i]+"\",\"column\":\"field_"+(i+1)+"\",\"type\":\"string\"}";
			}
			out=out+"]";
			System.out.println(out);
			
			/***
			if(s.length()>0) {
				try {
					ObjectMapper mapper = new ObjectMapper();
					List<?> listObj= mapper.readValue(out, ArrayList.class);							
					System.out.println("=====::"+listObj.size());
				}catch(Exception e) {
					e.printStackTrace();
				}				
				break;
			}
			****/
		}
    }
    
    private static List<?> getShowFields(String menuCode) throws Exception {
    	
        //显示列表字段
        String PPRPM001 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"180\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"加工量小计\",\"column\":\"x02\",\"type\":\"number\"},{\"name\":\"一般贸易加工量\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"来料加工量\",\"column\":\"x04\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x05\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPRPM002 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"入库量小计\",\"column\":\"x02\",\"type\":\"number\"},{\"name\":\"一般贸易入库量\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"来料入库量\",\"column\":\"x04\",\"type\":\"number\"},{\"name\":\"销售量小计\",\"column\":\"x05\",\"type\":\"number\"},{\"name\":\"国内销售\",\"column\":\"x06\",\"type\":\"number\"},{\"name\":\"来料销售\",\"column\":\"x07\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x08\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x09\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPRPM003 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"装置名称\",\"column\":\"project\",\"type\":\"string\",\"width\":\"150\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"开停工安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"检修天数\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x05\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPRPM004 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"收率\",\"column\":\"x02\",\"type\":\"percent\"},{\"name\":\"投入产出量\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"开工天数\",\"column\":\"x04\",\"type\":\"number\"},{\"name\":\"日均量\",\"column\":\"x05\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x07\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";

        String PPCPM001 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"指标\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCPM002 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"250\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"数量\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCPM003 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"250\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"标注\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"生产量\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"牌号切换时间\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"交库量\",\"column\":\"x05\",\"type\":\"string\"},{\"name\":\"销售量小计\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"化销外销量\",\"column\":\"x07\",\"type\":\"string\"},{\"name\":\"自销及互供量\",\"column\":\"x08\",\"type\":\"string\"},{\"name\":\"来料销售量\",\"column\":\"x09\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x10\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x11\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCPM004 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"装置名称\",\"column\":\"project\",\"type\":\"string\",\"width\":\"250\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"开停工安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"检修天数\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x05\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCPM005 = "[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"250\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"收率\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"投入产出量\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"开工天数\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"年产量\",\"column\":\"x05\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x07\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";

        String PPTWPM001="[{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"当月计划进厂量\",\"column\":\"x02\",\"type\":\"number\"},{\"name\":\"当月计划用量\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"下月指导进厂量\",\"column\":\"x04\",\"type\":\"number\"},{\"name\":\"下月指导用量\",\"column\":\"x05\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x07\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPTWPM002="[{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"外购（收）\",\"column\":\"x02\",\"type\":\"number\"},{\"name\":\"产量\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"外送量\",\"column\":\"x04\",\"type\":\"number\"},{\"name\":\"自用量\",\"column\":\"x05\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x07\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPTWPM003="[{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"外购（收）\",\"column\":\"x02\",\"type\":\"number\"},{\"name\":\"产量\",\"column\":\"x03\",\"type\":\"number\"},{\"name\":\"外送量\",\"column\":\"x04\",\"type\":\"number\"},{\"name\":\"自用量\",\"column\":\"x05\",\"type\":\"number\"},{\"name\":\"备注\",\"column\":\"x06\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x07\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPTWPM004="[{\"name\":\"装置名称\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"开停工安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"检修天数\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x05\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";

        String PPCIPM001="[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"数量\",\"excel_name\":\"计划安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCIPM002="[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"数量\",\"excel_name\":\"计划安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCIPM003="[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"数量\",\"excel_name\":\"计划安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCIPM004="[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"数量\",\"excel_name\":\"计划安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";
        String PPCIPM005="[{\"name\":\"是否标准化\",\"column\":\"isStandardization\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"indexLineCoding\",\"type\":\"string\"},{\"name\":\"项目\",\"column\":\"project\",\"type\":\"string\",\"width\":\"200\"},{\"name\":\"单位\",\"column\":\"unit\",\"type\":\"string\"},{\"name\":\"数量\",\"excel_name\":\"计划安排\",\"column\":\"x02\",\"type\":\"string\"},{\"name\":\"备注\",\"column\":\"x03\",\"type\":\"string\"},{\"name\":\"版本号\",\"column\":\"x01\",\"type\":\"string\"},{\"name\":\"排序\",\"column\":\"sortOrder\",\"type\":\"string\"},{\"name\":\"父级编码\",\"excel_name\":\"父级编辑\",\"column\":\"parentCode\",\"type\":\"string\"},{\"name\":\"标准码\",\"column\":\"standardCode\",\"type\":\"string\"},{\"name\":\"标准名称\",\"column\":\"standardName\",\"type\":\"string\"},{\"name\":\"计划类型\",\"column\":\"x04\",\"type\":\"string\"},{\"name\":\"数据类型\",\"column\":\"dataType\",\"type\":\"string\"}]";

    	List<Map<String,Object>> listField=new ArrayList<>();	
    	
    	String strJson = null;
    	if("PPRPM001".equals(menuCode)) { strJson = PPRPM001; }
    	if("PPRPM002".equals(menuCode)) { strJson = PPRPM002; }
    	if("PPRPM003".equals(menuCode)) { strJson = PPRPM003; }
    	if("PPRPM004".equals(menuCode)) { strJson = PPRPM004; }
    	    	
    	if("PPCPM001".equals(menuCode)) { strJson = PPCPM001; }
    	if("PPCPM002".equals(menuCode)) { strJson = PPCPM002; }
    	if("PPCPM003".equals(menuCode)) { strJson = PPCPM003; }
    	if("PPCPM004".equals(menuCode)) { strJson = PPCPM004; }
    	if("PPCPM005".equals(menuCode)) { strJson = PPCPM005; }
    	    	
    	if("PPTWPM001".equals(menuCode)) { strJson = PPTWPM001; }
    	if("PPTWPM002".equals(menuCode)) { strJson = PPTWPM002; }
    	if("PPTWPM003".equals(menuCode)) { strJson = PPTWPM003; }
    	if("PPTWPM004".equals(menuCode)) { strJson = PPTWPM004; }
    	
    	if("PPCIPM001".equals(menuCode)) { strJson = PPCIPM001; }
    	if("PPCIPM002".equals(menuCode)) { strJson = PPCIPM002; }
    	if("PPCIPM003".equals(menuCode)) { strJson = PPCIPM003; }
    	if("PPCIPM004".equals(menuCode)) { strJson = PPCIPM004; }
    	if("PPCIPM005".equals(menuCode)) { strJson = PPCIPM005; }
    	
    	if(strJson!=null) {
    		ObjectMapper mapper = new ObjectMapper();
    		List<?> listObj= mapper.readValue(strJson, ArrayList.class);							
    		return listObj;
    	}
    	
    	return null;
    }
    
    private static void readExcelDate()
    {
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\炼油板块月采集导入数据例子.xlsx";
    	
    	Object date=null;
		try {
			InputStream is = new FileInputStream(filePath);
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			Row row = null;
			String cell = null;
			// 循环输出表格中的内容			
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if(i==1) {
					date = row.getCell(1);
					break;
				}
				System.out.print(i + "行:");
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if(row.getCell(j)==null) {continue;}
					cell = row.getCell(j).toString();
					System.out.print(j+"["+cell + "]\t");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
		}
		
		if(date!=null) {
			Cell cell = (Cell) date;
			String strDate = cell.toString();
			System.out.println("===cell type =="+cell.getCellType()+"::"+cell);
			if(cell.getCellType()==CellType.NUMERIC) {
				String s=String.valueOf(Double.valueOf(cell.toString()).intValue());
				if(s.length()<6) {
					Date StartDate = HSSFDateUtil.getJavaDate(Double.valueOf(strDate));
					System.out.println("====1date is =="+StartDate);
				}else {
					System.out.println("====2date is ==");
				}
			}
			if(cell.getCellType()==CellType.STRING) {
				strDate=strDate.replace("年","").replace("月", "");				
				System.out.println("====3date is =="+strDate);//2019年10月
			}
		}
    }    
	
    private static void readData()
    {
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\data.xlsx";
    	
		int i = 0;
		try {
			InputStream is = new FileInputStream(filePath);
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			// 循环输出表格中的内容
			String nowMenuCode = null;
			Map<String,Object> nowTable = null;
			for (i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {				
				Row row = sheet.getRow(i);
				System.out.print((i+1) + "行");		
				if(row == null || row.getLastCellNum()<0) {
					System.out.println("");
					continue;
				}

				for (int j = 0; j < row.getLastCellNum(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if(row.getCell(j)==null) {
						System.out.print(j+"[NULL]\t");
						continue;
					}
					Cell cell = row.getCell(j);
					Object cellValue = null;
					if(cell.getCellType()==CellType.FORMULA) {
						boolean bRead=false;
						try {
							cellValue = cell.getNumericCellValue();
							bRead=true;
						}catch(Exception e) {
							//e.printStackTrace();
						}
						if(!bRead) {
							try {
								cellValue = cell.getCellFormula();
								bRead=true;
							}catch(Exception e) {
								//e.printStackTrace();
							}							
						}
					}
					if(cellValue == null) {
						cellValue=cell.toString();
					}
					
					System.out.print(j+"["+cellValue + "]\t");
				}
				System.out.println("");
				if(i>50) {break;}
			}
		} catch (Exception e) {
			System.out.println("读取到第"+(i+1)+"行出现异常:" + e);
			e.printStackTrace();
		}

    }   
    

    private static void readObject()
    {
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\炼油板块月采集导入数据例子.xlsx";
    	
    	Object obj=null;
		try {
			InputStream is = new FileInputStream(filePath);
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			Row row = sheet.getRow(6);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				
				System.out.println(j+"===cell type =="+cell.getCellType()+"::"+cell);
				if(cell.getCellType()==CellType.FORMULA) {
					try {
						System.out.println("001===cell value1 =="+cell.getNumericCellValue());
					}catch(Exception e) {e.printStackTrace();}
					try {
						System.out.println("002===cell value2 =="+cell.getCellFormula());
					}catch(Exception e) {e.printStackTrace();}
				}				
			}		
			
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
		}
		
    }        
}
