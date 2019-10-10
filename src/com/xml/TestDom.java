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
	 * ��ȡXML���ݵĲ���
	 */
	private static void readXML() {
		// ����XML��ͨ�ò���

		// 1������һ��XML�Ľ�����

		// 2������������XML�ļ�������һ��XML��Document�ڵ����(��ΪDocument�Ƕ�ȡ���ߴ��������ڵ�Ľڵ����)

		// 3��ͨ��Document�ڵ�����ȡ���ڵ�(Root�ڵ�)

		// 4����ȡ�����ڵ�󣬾Ϳ��Ի�ȡ���ڵ���µ��ӽ�����
		File xmlFile = new File(
				"D:\\WorkSpace6.5\\java1809\\Web_04\\WebRoot\\xml\\user.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(xmlFile);

			Element rootElement = document.getDocumentElement();
			System.out.println(rootElement.getNodeName());
			// getChildNodes�ǻ�ȡ���������ӽڵ�ļ��ϣ������հ׵Ľڵ�)
			NodeList nodeList = rootElement.getChildNodes();
			System.out.println(nodeList.getLength());

			nodeList = rootElement.getElementsByTagName("student");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element student = (Element) node;
				String id = student.getAttribute("id");
				// <stu_name>����</stu_name>
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
		String xmlStr = "<root><bigClass>2</bigClass><other_node>�����ڵ�</other_node></root>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// ���ַ���תΪһ���ֽ���
			ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlStr
					.getBytes("UTF-8"));
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			Element rootElement = document.getDocumentElement();
			System.out.println(rootElement.getNodeName());
			// getChildNodes�ǻ�ȡ���������ӽڵ�ļ��ϣ������հ׵Ľڵ�)
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
			stu_name.setTextContent("����");
			studentElement.appendChild(stu_name);

			Element stu_age = document.createElement("stu_age");
			stu_age.setTextContent("21");
			studentElement.appendChild(stu_age);

			Element stu_sex = document.createElement("stu_sex");
			stu_sex.setTextContent("��");
			studentElement.appendChild(stu_sex);
			// CDATA�ڵ�Ĳ���
			Element stu_content = document.createElement("stu_content");
			CDATASection cdataSection = document
					.createCDATASection("����CATA�ڵ�^%&%&*^%^&�����ַ�");
			stu_content.appendChild(cdataSection);
			studentElement.appendChild(stu_content);

			// ��������Student�ڵ�׷�ӵ�Root�ڵ�����
			root.appendChild(studentElement);
			// ����д�ص�XML�ļ��С�
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
			// getChildNodes�ǻ�ȡ���������ӽڵ�ļ��ϣ������հ׵Ľڵ�)
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

			// ����д�ص�XML�ļ��С�
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
			// getChildNodes�ǻ�ȡ���������ӽڵ�ļ��ϣ������հ׵Ľڵ�)
			NodeList nodeList = rootElement.getChildNodes();
			System.out.println(nodeList.getLength());

			nodeList = rootElement.getElementsByTagName("student");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element student = (Element) node;
				String id = student.getAttribute("id");
				if (id.equals("2")) {
					// ���¶�Ӧ��Ԫ��ֵ��
					Element stu_name = (Element) student.getElementsByTagName(
							"stu_name").item(0);
					stu_name.setTextContent("���Ƹ���");

					// CDATA�ڵ�ĸ���(��ɾ�ټ�)
					Element stu_content = (Element) student
							.getElementsByTagName("stu_content").item(0);
					stu_content.removeChild(stu_content.getFirstChild());
					CDATASection cdataSection = document
							.createCDATASection("��22222%^&�����ַ�");
					stu_content.appendChild(cdataSection);
					break;
				}
			}

			// ����д�ص�XML�ļ��С�
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
