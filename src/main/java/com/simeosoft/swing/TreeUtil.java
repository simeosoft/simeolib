/*
    The GUFF - The GNU Ultimate Framework Facility
    Copyright (C) Simeosoft di Carlo Simeone
 
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.
 
    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.
 
    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.simeosoft.swing;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDragEvent;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Thanks to Santosh!
 * @author santhosh kumar T - santhosh@in.fiorano.com
 */
public class TreeUtil{
    
    // is path1 descendant of path2
    public static boolean isDescendant(TreePath path1, TreePath path2){
        int count1 = path1.getPathCount();
        int count2 = path2.getPathCount();
        if(count1<=count2)
            return false;
        while(count1!=count2){
            path1 = path1.getParentPath();
            count1--;
        }
        return path1.equals(path2);
    }
    
    public static String getExpansionState(JTree tree, int row){
        TreePath rowPath = tree.getPathForRow(row);
        StringBuffer buf = new StringBuffer();
        int rowCount = tree.getRowCount();
        for(int i=row; i<rowCount; i++){
            TreePath path = tree.getPathForRow(i);
            if(i==row || isDescendant(path, rowPath)){
                if(tree.isExpanded(path))
                    buf.append(","+String.valueOf(i-row));
            }else
                break;
        }
        return buf.toString();
    }
    
    // FIXME - modificare routine per bug in caso di drag and drop
//    public static void getExpansionState2(JTree tree){
//        int rowCount = tree.getRowCount();
//        for(int i=0; i<rowCount; i++){
//            TreePath path = tree.getPathForRow(i);
//            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
//            MenuItem mi = (MenuItem) node.getUserObject();
//            if (node.)
//        }
//    }
    
    public static void restoreExpansionState(JTree tree, int row, String expansionState){
        StringTokenizer stok = new StringTokenizer(expansionState, ",");
        while(stok.hasMoreTokens()){
            int token = row + Integer.parseInt(stok.nextToken());
            tree.expandRow(token);
        }
    }
    
    /**
     * Verifica se si sta spostando il nodo source verso l'alto
     *
     * @param ntarget nodo target
     * @param nsource nodo source
     * @return <code>true</code> se i nodi hanno lo stesso parent e se il nodo target si trova sopra il
     * il nodo source, <code>false</code> altrimenti
     */
    public static boolean isMovingUp(DefaultMutableTreeNode ntarget,DefaultMutableTreeNode nsource) {
        boolean areSiblings;
        DefaultMutableTreeNode targetParent = (DefaultMutableTreeNode) ntarget.getParent();
        DefaultMutableTreeNode sourceParent = (DefaultMutableTreeNode) nsource.getParent();
//        areSiblings = (sourceParent.getUserObject().toString().equalsIgnoreCase(targetParent.getUserObject().toString()));
        areSiblings = (sourceParent.getUserObject().equals(targetParent.getUserObject()));
        if(areSiblings) {
            int targetIndex = targetParent.getIndex(ntarget);
            int sourceIndex = sourceParent.getIndex(nsource);
            //sposta in base all'indice
            if(sourceIndex>targetIndex) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    
    /**
     * Restiuisce il nodo target
     *
     * @param targetTree JTree target
     * @param dtde DropTargetDragEvent
     * @return DefaultMutableTreeNode target
     */
    public static DefaultMutableTreeNode getNodeForEvent(JTree targetTree,DropTargetDragEvent dtde) {
        Point p = dtde.getLocation();
        TreePath path = targetTree.getClosestPathForLocation(p.x, p.y);
        return (DefaultMutableTreeNode) path.getLastPathComponent();
    }
    
    /**
     * Richiama il metodo paintImmediately() del JTree
     *
     * @param tree
     */
    public static void paint(JTree tree) {
        Rectangle r = tree.getBounds();
        int rowVisible = tree.getVisibleRowCount();
        Rectangle rv = new Rectangle(r.width,r.height+rowVisible);
        tree.paintImmediately(rv);
    }
    
    /**
     * Collapse the tree
     * @param tree the tree to collapse
     */
    public static void collapseTree(JTree tree) {
        final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        final DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        collapseSubTree(tree, root, model);
    }
    
    /**
     * Collapse the subtree starting at <code>startNode</code> node
     * @param tree the tree to collapse
     * @param startNode the starting point
     * @param model the tree model
     */
    private static void collapseSubTree(JTree tree, DefaultMutableTreeNode startNode, DefaultTreeModel model) {
        if(startNode.isLeaf()) {
            return;
        }
        for (Enumeration e = startNode.children(); e.hasMoreElements();) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            collapseSubTree(tree, node, model);
        }
        TreePath tp = new TreePath(model.getPathToRoot(startNode));
        if (tree.isExpanded(tp)) {
            tree.collapsePath(tp);
            // senza questa istruzione, in alcuni casi non riusciamo a riportare
            // lo scrolling ad un nodo espanso
            tree.scrollPathToVisible(tp);
        }
    }
    
    
    
}