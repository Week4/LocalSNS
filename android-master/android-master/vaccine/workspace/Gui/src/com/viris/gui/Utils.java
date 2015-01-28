package com.viris.gui;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FileUtils;

/**
 * Utils
 */
public class Utils {

	public static boolean locked = false;
	
	static public void expandAll(JTree tree, boolean expand) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root), expand);
	}

	static public String getBeanshellCMDS(String file){
		try {
			return FileUtils.readFileToString(new File(file));
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return null;
	}
	
	static private void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (@SuppressWarnings("unchecked")
			Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
				TreeNode treeNode = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(treeNode);
				expandAll(tree, path, expand);
			}
		}

		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}
	
	

}