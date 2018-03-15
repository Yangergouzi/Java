package com.yang.imageViewer;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ViewerService {
	public List cacheFiles = null;//���ͬһ�ļ���������ͼƬ
	File selectedFile = null;//������ѡ�е��ļ�
	String filePath = null;//�����ļ�·��
	File currentDir = null;//������ǰ�ļ�����Ŀ¼
	File dir = null;//֮ǰ���ļ���
	Double range = 0.2;//�������ű�����Χ
	
	ViewerFrame frame = null;
	
	public ViewerService(ViewerFrame frame) {
		this.frame = frame;
	}

	/*
	 * ��ͼƬ�ļ������Ѹ��ļ������ļ���������ͼƬ�ļ���������
	 */
	public void open() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg/png/gif Images", "jpg","png","gif");
		chooser.setFileFilter(filter);
		if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {//���ѡ��ĳ�ļ�
			dir = currentDir;//����֮ǰ�ļ��У����뵱ǰ���Ƚ�
			//��ȡ��ǰ�ļ�
			selectedFile = chooser.getSelectedFile();
			//��ȡ��ǰ�ļ�·��
			filePath = selectedFile.getAbsolutePath();
			currentDir = chooser.getCurrentDirectory();//���µ�ǰ�ļ���
			//���֮ǰ�ļ���Ϊ�ջ�ǰ�ļ�����֮ǰ��ͬ
			if(dir == null || !dir.equals(currentDir)) {				
				File[] files = currentDir.listFiles();
				cacheFiles = new ArrayList<File>();
				//���浱ǰ�ļ���������ͼƬ�ļ�
				FileFilter[] filters = chooser.getChoosableFileFilters();
				for(File file : files) {
					for(FileFilter fileFilter : filters) {
						if(fileFilter.accept(file)) {
							cacheFiles.add(file);break;
						}
					}
				}				
			}	
		}
		//��ȡͼƬ
		ImageIcon image = new ImageIcon(filePath);
		frame.getLabel().setIcon(image);		
	}
	
	/*
	 * ����ͼƬ
	 */
	public void zoom(boolean isBig) {
		ImageIcon icon = (ImageIcon) frame.getLabel().getIcon();//�õ�ԭͼƬ
		//���label����ͼƬ
		if(icon != null) {
			Double ratio = isBig ? 1 + range : 1 - range;//���ݷŴ����Сȷ������
			int width = (int) (icon.getIconWidth() * ratio);//�õ����ź���
			Image img = icon.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT);
			ImageIcon newIcon = new ImageIcon(img);
			frame.getLabel().setIcon(newIcon);
		}
	}
	/*
	 * ��һ��
	 */
	public void last() {
		//����л���ͼƬ
		if(cacheFiles != null && !cacheFiles.isEmpty()) {
			int index = cacheFiles.indexOf(selectedFile);//��ȡ��ǰ�ļ��ڻ����λ��
			if(index > 0) {
				File file = (File) cacheFiles.get(index - 1);//��ȡ��һ���ļ�
				String path = file.getAbsolutePath();
				ImageIcon last = new ImageIcon(path);
				frame.getLabel().setIcon(last);		
				selectedFile = file;
			}
		}
	}
	/*
	 * ��һ��
	 */
	public void next() {
		//����л���ͼƬ
		if(cacheFiles != null && !cacheFiles.isEmpty()) {
			int index = cacheFiles.indexOf(selectedFile);//��ȡ��ǰ�ļ��ڻ����λ��
			if(index > 0) {
				if((index + 1) < cacheFiles.size()) {
					File file = (File) cacheFiles.get(index + 1);//��ȡ��һ���ļ�
					String path = file.getAbsolutePath();
					ImageIcon last = new ImageIcon(path);
					frame.getLabel().setIcon(last);		
					selectedFile = file;
				}
			}
		}
	}
}
