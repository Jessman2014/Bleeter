package bleeter.users.avatar;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import bleeter.users.BleetUser;
import bleeter.users.UserServices;

@Service
public class AvatarServices {
	@Autowired
	private UserServices userServices;
	
	public static final int WIDTH = 150, HEIGHT=150;
	
	public String getAvatarFile(String uid) {
		File root = FileSystemView.getFileSystemView().getRoots()[0];
		return root.getAbsolutePath() + File.separator + "tmp" + File.separator + "bleeter" + File.separator + uid;
	}
	
	
	// truncate to WIDTH x HEIGHT
	// and convert to PNG
	public BufferedImage preProcessAvatarImage(BufferedImage image) {
		int x, y, dim;
		if(image.getWidth() < image.getHeight()) {
			x = 0;
			y = (image.getHeight() - image.getWidth()) / 2;
			dim = image.getWidth();		
		} else {
			y = 0;
			x = (image.getWidth() - image.getHeight())/ 2;
			dim = image.getHeight();
		}
				
		image =  image.getSubimage(x, y, dim, dim);		
		BufferedImage result = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, 200, 200, null);
		g.dispose();
		return result;
	}

	public byte[] getImageData(String uid) throws IOException {
		BleetUser user = userServices.findById(uid);
		String avatar = user.getAvatar();
		if(avatar != null) {
			return FileCopyUtils.copyToByteArray(new File(avatar));
		}
		return null;
	}

	public BleetUser uploadAvatar(BleetUser user, MultipartFile file) throws IOException {
		BufferedImage image = preProcessAvatarImage(ImageIO.read(file.getInputStream()));
		String avatar = getAvatarFile(user.getId());
		ImageIO.write(image, "PNG", new File(avatar));		
		user.setAvatar(avatar);
		return userServices.updateUser(user);
	}
}