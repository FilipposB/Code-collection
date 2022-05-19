package gui.display;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FileExplorer {
	public boolean active;
	public String name;
	public String path;
	public FileExplorer(boolean save,String nam){
		active = true;
		name = null;
		path = null;
		if (save){
			save(nam);
		}else{
			open(".goz");
		}
	}
	public FileExplorer(boolean save,String nam,String type){
		active = true;
		name = null;
		path = null;
		if (save){
			save(nam);
		}else{
			open(type);
		}
	}
	private void save(String name){
		JTextField filename = new JTextField(), dir = new JTextField();
		 filename.setEditable(false);
		JFileChooser c = new JFileChooser();
		c.setSelectedFile(new File(name));
		   // Demonstrate "Save" dialog:
		   int rVal = c.showSaveDialog(null);
		   if (rVal == JFileChooser.APPROVE_OPTION) {
			     filename.setText(c.getSelectedFile().getName());

		     dir.setText(c.getCurrentDirectory().toString());
		     path=c.getCurrentDirectory().toString();
		   }
		   if (rVal == JFileChooser.CANCEL_OPTION) {
		     dir.setText("");
		     active = false;
		   }
	}
	private void open(String type){
		JTextField filename = new JTextField(), dir = new JTextField();
		   JFileChooser c = new JFileChooser();
		   c.setFileFilter(new FileFilter() {
		        @Override
		        public boolean accept(File f) {
		            if (f.isDirectory()) {
		                return true;
		            }
		            final String name = f.getName();
		            return name.endsWith(type);
		        }

		        @Override
		        public String getDescription() {
		            return "*"+type;
		        }
		    });
		   // Demonstrate "Open" dialog:
		   int rVal = c.showOpenDialog(null);
		   if (rVal == JFileChooser.APPROVE_OPTION) {
		     filename.setText(c.getSelectedFile().getName());
		     dir.setText(c.getCurrentDirectory().toString());
			   path = c.getCurrentDirectory().toString();
			   name = c.getSelectedFile().getName();
		   }
		   if (rVal == JFileChooser.CANCEL_OPTION) {
		     active = false;
		   }
		 }
	}

