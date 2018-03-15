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
	public List cacheFiles = null;//存放同一文件夹下所有图片
	File selectedFile = null;//声明被选中的文件
	String filePath = null;//声明文件路径
	File currentDir = null;//声明当前文件所在目录
	File dir = null;//之前的文件夹
	Double range = 0.2;//设置缩放比例范围
	
	ViewerFrame frame = null;
	
	public ViewerService(ViewerFrame frame) {
		this.frame = frame;
	}

	/*
	 * 打开图片文件，并把该文件所在文件夹内所有图片文件缓存起来
	 */
	public void open() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg/png/gif Images", "jpg","png","gif");
		chooser.setFileFilter(filter);
		if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {//如果选中某文件
			dir = currentDir;//保存之前文件夹，好与当前作比较
			//获取当前文件
			selectedFile = chooser.getSelectedFile();
			//获取当前文件路径
			filePath = selectedFile.getAbsolutePath();
			currentDir = chooser.getCurrentDirectory();//更新当前文件夹
			//如果之前文件夹为空或当前文件夹与之前不同
			if(dir == null || !dir.equals(currentDir)) {				
				File[] files = currentDir.listFiles();
				cacheFiles = new ArrayList<File>();
				//缓存当前文件夹下所有图片文件
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
		//获取图片
		ImageIcon image = new ImageIcon(filePath);
		frame.getLabel().setIcon(image);		
	}
	
	/*
	 * 缩放图片
	 */
	public void zoom(boolean isBig) {
		ImageIcon icon = (ImageIcon) frame.getLabel().getIcon();//得到原图片
		//如果label里有图片
		if(icon != null) {
			Double ratio = isBig ? 1 + range : 1 - range;//根据放大或缩小确定比例
			int width = (int) (icon.getIconWidth() * ratio);//得到缩放后宽度
			Image img = icon.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT);
			ImageIcon newIcon = new ImageIcon(img);
			frame.getLabel().setIcon(newIcon);
		}
	}
	/*
	 * 上一张
	 */
	public void last() {
		//如果有缓存图片
		if(cacheFiles != null && !cacheFiles.isEmpty()) {
			int index = cacheFiles.indexOf(selectedFile);//获取当前文件在缓存的位置
			if(index > 0) {
				File file = (File) cacheFiles.get(index - 1);//获取上一个文件
				String path = file.getAbsolutePath();
				ImageIcon last = new ImageIcon(path);
				frame.getLabel().setIcon(last);		
				selectedFile = file;
			}
		}
	}
	/*
	 * 下一张
	 */
	public void next() {
		//如果有缓存图片
		if(cacheFiles != null && !cacheFiles.isEmpty()) {
			int index = cacheFiles.indexOf(selectedFile);//获取当前文件在缓存的位置
			if(index > 0) {
				if((index + 1) < cacheFiles.size()) {
					File file = (File) cacheFiles.get(index + 1);//获取下一个文件
					String path = file.getAbsolutePath();
					ImageIcon last = new ImageIcon(path);
					frame.getLabel().setIcon(last);		
					selectedFile = file;
				}
			}
		}
	}
}
