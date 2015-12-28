package com.dl.rmas.common.utils;

import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.print.Book;  
import java.awt.print.PageFormat;  
import java.awt.print.Paper;  
import java.awt.print.Printable;  
import java.awt.print.PrinterException;  
import java.awt.print.PrinterJob;  
import java.util.Map;

/**
 *  打印标签
 * 
 * @author dongbz 2015-7-31
 */
public class PrintLabel implements Printable {  

	private Map<String, String> args;
	
    public void setArgs(Map<String, String> args) {
		this.args = args;
	}

	@Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {  
  
        if (page > 0) {  
            return NO_SUCH_PAGE;  
        }  
  
        Graphics2D g2d = (Graphics2D) g;  
        
        g2d.drawLine(4, 13, 173, 13);
        g2d.drawLine(4, 116, 173, 116);
        g2d.drawLine(4, 13, 4, 116);
        g2d.drawLine(173, 13, 173, 116);
        
        Font bold = new Font("Arial", Font.BOLD, 7);
        Font plain = new Font("Arial", Font.PLAIN, 7);
        
        
        int height = 24, jg = 14;
        g2d.setFont(bold);  
        g2d.drawString("RMA No.", 6, height);
        g2d.drawString("Item No.", 110, height);
        
        g2d.setFont(plain);  
        g2d.drawString(args.get("rma.no"), 40, height);  
        g2d.drawString(args.get("item.no"), 145, height);  
        
        height += jg;
        g2d.setFont(bold);  
        g2d.drawString("SN", 6, height);
        g2d.setFont(plain);  
        g2d.drawString(args.get("sn"), 40, height);  
        
        height += jg;
        g2d.setFont(bold);  
        g2d.drawString("Part Cust", 6, height);
        g2d.drawString("Warr", 110, height);
        g2d.setFont(plain);  
        g2d.drawString(args.get("part.cust"), 40, height);
        g2d.drawString(args.get("warr"), 134, height);  
        
        height += jg;
        g2d.setFont(bold);  
        g2d.drawString("Brand", 6, height);
        g2d.drawString("R.T.", 110, height);
       
        g2d.setFont(plain);  
        g2d.drawString(args.get("brand"), 40, height);  
        g2d.drawString(args.get("r.t"), 140, height);  
        
        height += jg;
        g2d.setFont(bold);  
        g2d.drawString("Type", 6, height);
        g2d.setFont(plain); 
        g2d.drawString(args.get("type"), 41, height);  
        
        height += jg;
        g2d.setFont(bold);
        g2d.drawString("Desc", 6, height);
        
        g2d.setFont(plain); 
//        g2d.drawString(args.get("desc"), 40, height);  
        g2d.drawString(args.get("customer.fault.desc"), 41, height);
        
        height += jg;
        g2d.setFont(bold);  
        g2d.drawString("MB Model", 6, height);
        
        g2d.setFont(plain); 
        g2d.drawString(args.get("mb.model"), 44, height);  
        
        return PAGE_EXISTS;  
    }  
  
    public static void main(String[] args) {  
        int height = 113;  
  
        // 通俗理解就是书、文档  
        Book book = new Book();  
  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT);  
  
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(175, 113); 
        p.setImageableArea(0, 0, 175, height);  
        pf.setPaper(p);  
  
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        book.append(new PrintLabel(), pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
        try {  
        	if (job.printDialog()) {
        		job.print();  
			}
        } catch (PrinterException e) {  
            e.printStackTrace();
        }  
    }  
  
}  
