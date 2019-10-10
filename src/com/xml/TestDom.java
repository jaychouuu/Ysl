package com.xml;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestDom {
	public static void main(String[] args) {
		// TestDom.readXML();
		// TestDom.readXMLForString();
		// TestDom.addXML();
		TestDom.updateXML();
		// TestDom.deleteXML();
	}

	/**
	 * 读取XML数据的操作
	 */
	private static void readXML() {
		// 解析XML的通用步骤

		// 1：创建一个XML的解析器

		// 2：解析器解析XML文件，返回一个XML的Document节点对象。(因为Document是读取或者创建其他节点的节点对象)

		// 3：通过Document节点来获取根节点(Root节点)

		// 4：获取到根节点后，就可以获取根节点底下的子结点对象
		File xmlFile = new File(
				"D:\\WorkSpace6.5\\java1809\\Web_04\\WebRoot\\xml\\user.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(xmlFile);

			Element rootElement = document.getDocumentElement();
			System.out.println(rootElement.getNodeName());
			// getChildNodes是获取底下所有子节点的集合（包括空白的节点)
			NodeList nodeList = rootElement.getChildNodes();
			System.out.println(nodeList.getLength());

			nodeList = rootElement.getElementsByTagName("student");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element student = (Element) node;
				String id = student.getAttribute("id");
				// <stu_name>张三</stu_name>
				Element stu_nameElement = (Element) student
						.getElementsByTagName("stu_name").item(0);
				String stu_name = stu_nameElement.getTextContent();

				String stu_age = student.getElementsByTagName("stu_age")
						.item(0).getTextContent();

				String stu_sex = student.getElementsByTagName("stu_sex")
						.item(0).getTextContent();

				String stu_content = student
						.getElementsByTagName("stu_content").item(0)
						.getTextContent();

				System.out.println(id + "\t" + stu_name + "\t" + stu_sex + "\t"
						+ stu_age + "\t" + stu_content);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readXMLForString() {
		String xmlStr = "<root><bigClass>2</bigClass><other_node>其他节点</other_node></root>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// 将字符串转为一个字节流
			ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlStr
					.getBytes("UTF-8"));
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			Element rootElement = document.getDocumentElement();
			System.out.println(rootElement.getNodeName());
			// getChildNodes是获取底下所有子节点的集合（包括空白的节点)
			NodeList nodeList = rootElement.getChildNodes();

			String bigClass = nodeList.item(0).getTextContent();
			String other_node = nodeList.item(1).getTextContent();

			System.out.println("bigClass = " + bigClass);
			System.out.println("other_node = " + other_node);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addXML() {
		File xmlFile = new File(
				"D:\\WorkSpace6.5\\java1809\\Web_04\\WebRoot\\xml\\user.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			Element root = document.getDocumentElement();

			Element studentElement = document.createElement("student");
			studentElement.setAttribute("id", "3");

			Element stu_name = document.createElement("stu_name");
			stu_name.setTextContent("王五");
			studentElement.appendChild(stu_name);

			Element stu_age = document.createElement("stu_age");
			stu_age.setTextContent("21");
			studentElement.appendChild(stu_age);

			Element stu_sex = document.createElement("stu_sex");
			stu_sex.setTextContent("男");
			studentElement.appendChild(stu_sex);
			// CDATA节点的操作
			Element stu_content = document.createElement("stu_content");
			CDATASection cdataSection = document
					.createCDATASection("这是CATA节点^%&%&*^%^&特殊字符");
			stu_content.appendChild(cdataSection);
			studentElement.appendChild(stu_content);

			// 将创建的Student节点追加到Root节点下面
			root.appendChild(studentElement);
			// 重新写回到XML文件中。
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteXML() {
		File xmlFile = new File(
				"D:\\WorkSpace6.5\\java1809\\Web_04\\WebRoot\\xml\\user.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(xmlFile);

			Element rootElement = document.getDocumentElement();
			System.out.println(rootElement.getNodeName());
			// getChildNodes是获取底下所有子节点的集合（包括空白的节点)
			NodeList nodeList = rootElement.getChildNodes();
			System.out.println(nodeList.getLength());

			nodeList = rootElement.getElementsByTagName("student");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element student = (Element) node;
				String id = student.getAttribute("id");
				if (id.equals("3")) {
					rootElement.removeChild(student);
					break;
				}
			}

			// 重新写回到XML文件中。
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void updateXML() {
		File xmlFile = new File(
				"D:\\WorkSpace6.5\\java1809\\Web_04\\WebRoot\\xml\\user.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(xmlFile);

			Element rootElement = document.getDocumentElement();
			System.out.println(rootElement.getNodeName());
			// getChildNodes是获取底下所有子节点的集合（包括空白的节点)
			NodeList nodeList = rootElement.getChildNodes();
			System.out.println(nodeList.getLength());

			nodeList = rootElement.getElementsByTagName("student");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element student = (Element) node;
				String id = student.getAttribute("id");
				if (id.equals("2")) {
					// 更新对应的元素值。
					Element stu_name = (Element) student.getElementsByTagName(
							"stu_name").item(0);
					stu_name.setTextContent("名称更新");

					// CDATA节点的更新(先删再加)
					Element stu_content = (Element) student
							.getElementsByTagName("stu_content").item(0);
					stu_content.removeChild(stu_content.getFirstChild());
					CDATASection cdataSection = document
							.createCDATASection("这22222%^&特殊字符");
					stu_content.appendChild(cdataSection);
					break;
				}
			}

			// 重新写回到XML文件中。
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
