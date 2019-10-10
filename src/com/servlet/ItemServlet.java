package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.bean.ClassInfo;
import com.bean.ItemBean;
import com.google.gson.Gson;
import com.service.ClassInfoService;
import com.service.ItemService;
import com.util.DateUtil;
import com.util.GlobalFadace;
import com.util.PageUtil;
import com.util.ResultInfo;

public class ItemServlet extends HttpServlet {

	private ItemService itemService;
	private ClassInfoService classInfoService;
	private PrintWriter out;

	/**
	 * Constructor of the object.
	 */
	public ItemServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request  the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException      if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		out = response.getWriter();

		String task = request.getParameter("task");
		itemService = GlobalFadace.getItemService();
		classInfoService = GlobalFadace.getClassInfoService();

		if (task.equals("list")) {
			this.listtask(request, response);
		} else if (task.equals("add")) {
			this.addtask(request, response);

		} else if (task.equals("loadsmall")) {
			try {
				this.loadsmall(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (task.equals("save")) {
			this.Save(request, response);
		} else if (task.equals("edit")) {
			this.edit(request, response);
		} else if (task.equals("modify")) {
			this.modify(request, response);
		} else if (task.equals("delete")) {

			this.delete(request, response);

		}

		out.flush();
		out.close();

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String itemid = request.getParameter("itemid");

		ResultInfo resultInfo = new ResultInfo();

		try {

			ItemBean itemBean = itemService.getItemBeanByID(itemid);
			String filepath = itemBean.getFilepath();
			itemService.delete(itemid);
			if (filepath != null && !filepath.equals("")) {
				ServletContext context = this.getServletContext();
				String uploadPath = context.getRealPath("/uploadfiles");
				File uploadFile = new File(uploadPath + "/" + filepath);

				if (uploadFile.exists()) {

					uploadFile.delete();

				}

			}

			resultInfo.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setFlag(false);
			resultInfo.setMessage(e.getMessage());
		}
		String jsonStr = new Gson().toJson(resultInfo);
		out.print(jsonStr);

	}

	private void modify(HttpServletRequest request, HttpServletResponse response) {

		boolean upload_error = false;
		String error_message = null;
		Map<String, String> valueMap = new HashMap<String, String>();
		ItemBean oldBean = null;
		String uploadPath = null;
		ServletContext context;
		try {

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			List<FileItem> fileItemList = fileUpload.parseRequest(request);

			for (FileItem fileItem : fileItemList) {
				boolean isFormField = fileItem.isFormField();

				if (isFormField == true) {
					// 表单元素
					String field_Name = fileItem.getFieldName();
					String field_Value = fileItem.getString("UTF-8");
					valueMap.put(field_Name, field_Value);

				} else {
					// 文件元素
					String itemid = valueMap.get("itemid");

					String filename = fileItem.getName();
					if (filename != null && !filename.equals("")) {

						oldBean = itemService.getItemBeanByID(itemid);

						String filepath = DateUtil.getTimeStamp() + "_" + filename;

						valueMap.put("filename", filename);
						valueMap.put("filepath", filepath);

						try {
							context = this.getServletContext();
							uploadPath = context.getRealPath("/uploadfiles");

							File uploadFile = new File(uploadPath + "/" + filepath);

							InputStream inputStream = fileItem.getInputStream();

							OutputStream outputStream = new FileOutputStream(uploadFile);

							// common-io中的方法。
							IOUtils.copy(inputStream, outputStream);

							// 关闭
							IOUtils.closeQuietly(inputStream);
							IOUtils.closeQuietly(outputStream);
						} catch (Exception e) {

							e.printStackTrace();

						}

					}

				}
			}

		} catch (Exception e) {

			upload_error = true;

			error_message = e.getMessage();
			e.printStackTrace();
		}
		ResultInfo resultInfo = new ResultInfo();

		if (upload_error == true) {
			resultInfo.setFlag(false);
			resultInfo.setMessage(error_message);
		} else {

			try {
				// 删除旧文件
				if (oldBean != null) {

					String oldfilename = oldBean.getFilename();
					String oldfilepath = oldBean.getFilepath();
					String filename = valueMap.get("filename");
					if (filename != null && filename.equals("")) {

						if (oldfilename != null && !oldfilename.equals("")) {
							// 删除源文件
							File file = new File(uploadPath + "/" + oldfilepath);
							if (file.exists()) {
								file.delete();

							}

						}
					} else {
						// 没有文件上传
						valueMap.put("filename", oldfilename);
						valueMap.put("filepath", oldfilepath);
					}

				}

				itemService.modify(valueMap);
				resultInfo.setFlag(true);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());

			}
		}
		String jsonStr = new Gson().toJson(resultInfo);

		out.print(jsonStr);

	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		String itemid = request.getParameter("itemid");
		try {

			ItemBean itemBean = itemService.getItemBeanByID(itemid);
			List<ClassInfo> biglist = classInfoService.getBigList();
			List<ClassInfo> smalllist = classInfoService.getSmallList(itemBean.getBigclass());
			request.setAttribute("biglist", biglist);
			request.setAttribute("smalllist", smalllist);
			request.setAttribute("itemBean", itemBean);
			request.getRequestDispatcher("/admin/item/edit.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void Save(HttpServletRequest request, HttpServletResponse response) {
		boolean upload_error = false;
		String error_message = null;

		Map<String, String> valueMap = new HashMap<String, String>();

		try {

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			List<FileItem> fileItemList = fileUpload.parseRequest(request);

			for (FileItem fileItem : fileItemList) {
				boolean isFormField = fileItem.isFormField();

				if (isFormField == true) {
					// 表单元素
					String field_Name = fileItem.getFieldName();
					String field_Value = fileItem.getString("UTF-8");
					valueMap.put(field_Name, field_Value);

				} else {
					// 文件元素
					String filename = fileItem.getName();
					if (filename != null & !filename.equals("")) {

						String filepath = DateUtil.getTimeStamp() + "_" + filename;

						valueMap.put("filename", filename);
						valueMap.put("filepath", filepath);

						try {
							ServletContext context = this.getServletContext();
							String uploadPath = context.getRealPath("/uploadfiles");

							File uploadFile = new File(uploadPath + "/" + filepath);

							InputStream inputStream = fileItem.getInputStream();

							OutputStream outputStream = new FileOutputStream(uploadFile);

							// common-io中的方法。
							IOUtils.copy(inputStream, outputStream);

							// 关闭
							IOUtils.closeQuietly(inputStream);
							IOUtils.closeQuietly(outputStream);
						} catch (Exception e) {

							e.printStackTrace();

						}

					}

				}
			}

		} catch (Exception e) {

			upload_error = true;

			error_message = e.getMessage();
			e.printStackTrace();
		}
		ResultInfo resultInfo = new ResultInfo();

		if (upload_error == true) {

			resultInfo.setFlag(false);
			resultInfo.setMessage(error_message);
		} else {

			try {

				itemService.save(valueMap);
				resultInfo.setFlag(true);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());

			}
		}
		String jsonStr = new Gson().toJson(resultInfo);

		out.print(jsonStr);
	}

	private void loadsmall(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, Exception {

		String parentid = request.getParameter("parentid");
		List<ClassInfo> smalllist = classInfoService.getSmallList(Integer.parseInt(parentid));
		String jsonStr = new Gson().toJson(smalllist);
		out.print(jsonStr);

	}

	private void addtask(HttpServletRequest request, HttpServletResponse response) {
		try {

			String addtime = DateUtil.getNowTime();

			List<ClassInfo> biglist = classInfoService.getBigList();
			request.setAttribute("biglist", biglist);
			request.setAttribute("systime", addtime);

			request.getRequestDispatcher("/admin/item/add.jsp").forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void listtask(HttpServletRequest request, HttpServletResponse response) {

		String item_name = request.getParameter("item_name");
		String addtime_start = request.getParameter("addtime_start");
		String addtime_end = request.getParameter("addtime_end");
		String item_class = request.getParameter("item_class");
		String price_begin = request.getParameter("price_begin");
		String price_end = request.getParameter("price_end");

		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("item_name", item_name);
		paramMap.put("addtime_start", addtime_start);
		paramMap.put("addtime_end", addtime_end);
		paramMap.put("item_class", item_class);
		paramMap.put("price_begin", price_begin);
		paramMap.put("price_end", price_end);

		// 用于下拉框的显示
		try {
			PageUtil pageUtil = new PageUtil(request);
			int rsCount = itemService.getItemCount(paramMap);
			int pageSize = 10;

			pageUtil.setPageSize(pageSize);
			pageUtil.setRsCount(rsCount);

			int pageCount = pageUtil.getPageCount();
			int currentPage = pageUtil.getCurrentPage();

			// 生成工具栏
			String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

			int startIndex = (currentPage - 1) * pageSize;
			int endIndex = pageSize;

			List<ItemBean> itemlist = itemService.getItemList(paramMap, startIndex, endIndex);

			// 用于列表的显示

			request.setAttribute("itemlist", itemlist);
			request.setAttribute("pageTool", pageTool);
			List<ClassInfo> classlist = new ArrayList<ClassInfo>();

			classlist = classInfoService.getList();
			request.setAttribute("classlist", classlist);
			request.getRequestDispatcher("/admin/item/list.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
