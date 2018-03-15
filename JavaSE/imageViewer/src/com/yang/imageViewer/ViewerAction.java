package com.yang.imageViewer;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ViewerAction extends AbstractAction {
	ImageIcon icon;
	String name;
	ViewerFrame vFrame;

	public ViewerAction(ImageIcon icon, String name, ViewerFrame vFrame) {
		super(name,icon);
		this.icon = icon;
		this.name = name;
		this.vFrame = vFrame;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}
