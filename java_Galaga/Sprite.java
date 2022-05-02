import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprite {
	private Image img; //이미지 저장
	protected int ix,iy; //이미지 크기
	protected int x,y; // 좌표
	protected int dx, dy; // 이동량
	
	//생성자 정의
	public Sprite(String s) {
		ImageIcon sub_img = new ImageIcon(s);
		img = sub_img.getImage();
		//이동량 정의
		dx = 0;
		dy = 0;
	}
	
	// 설정자 정의
	public Image getImg() {	return img;	}
	public void setImg(Image img) {	this.img = img;	}
	public int getIx() {return ix;}
	public void setIx(int ix) {	this.ix = ix;}
	public int getIy() {return iy;}
	public void setIy(int iy) {	this.iy = iy;}
	public int getX() {	return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {	return y;}
	public void setY(int y) {this.y = y;}
	public int getDx() {return dx;}
	public void setDx(int dx) {	this.dx = dx;}
	public int getDy() {return dy;}
	public void setDy(int dy) {	this.dy = dy;}
	
	
	// draw함수 정의
	public void draw(Graphics g) {
		// 불러온 이미지를 정해진 변수에 맞춰 그려준다.
		g.drawImage(img, getX(), getY(), getIx(), getIy(), null);
	}
	
	public void move() {
		x += dx;
		y += dy;
	}

	public boolean checkCollision(Sprite other) {
		Rectangle myRect = new Rectangle();
		Rectangle otherRect = new Rectangle();
		myRect.setBounds(getX(), getY(), getIx(), getIy());
		otherRect.setBounds(other.getX(), other.getY(),
				other.getIx(), other.getIy());
		return myRect.intersects(otherRect);
	}
	public void handleCollision(Sprite other) {}
}

