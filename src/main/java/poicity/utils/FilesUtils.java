package poicity.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import poicity.ConfigIni;

public class FilesUtils {

	public static String immagazzinaImg(MultipartFile file, Long idUser) {
		String newPath = "";

		Path pathXimg = Paths.get(new File("").getAbsolutePath() + "\\img");

		try {
			Files.createDirectories(pathXimg);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		String nuovoNome = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS"));
		String nuovoNome = String.valueOf(idUser);
		File fileTo = new File(
				pathXimg.toString() + "\\" + nuovoNome + "." + FilenameUtils.getExtension(file.getOriginalFilename()));

		try {
			file.transferTo(fileTo);
			newPath = fileTo.getAbsolutePath();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newPath;
	}

//	public static String immagazzinaAvatarDefault(Long idUser) {
//		String newPath = "";
//		File defaultAvatar = new File("src\\main\\java\\poicity\\utils\\img\\dafault_avatar.png");
//
//		Path pathXimg = Paths.get(new File("").getAbsolutePath() + "\\img");
//
//		try {
//			Files.createDirectories(pathXimg);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////		String nuovoNome = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS"));
//		String nuovoNome = String.valueOf(idUser);
//		File fileTo = new File(pathXimg.toString() + "\\" + nuovoNome + "." + FilenameUtils.getExtension(defaultAvatar.getName()));
//
//		try {
//			Files.copy(Paths.get(defaultAvatar.getAbsolutePath()), Paths.get(fileTo.getAbsolutePath()),
//					StandardCopyOption.REPLACE_EXISTING);
//			newPath = fileTo.getAbsolutePath();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return newPath;
//	}

	public static String immagazzinaAvatarDefault2(Long idUser) {
		String newPath = "";
//		File defaultAvatar = new File("src\\main\\java\\poicity\\utils\\img\\dafault_avatar.png");
		BufferedInputStream in = null;
		try {
//			in = new BufferedInputStream(new URL("https://i.ibb.co/xGZtcXS/dafault-avatar.png").openStream());
			in = new BufferedInputStream(new URL(ConfigIni.OnlineLinkDefaultAvatar()).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path pathXimg = Paths.get(new File("").getAbsolutePath() + "\\img");

		try {
			Files.createDirectories(pathXimg);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		String nuovoNome = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS"));
		String nuovoNome = String.valueOf(idUser);
		File fileTo = new File(pathXimg.toString() + "\\" + nuovoNome + ".png");

//        System.out.println(fileTo.getAbsolutePath());
		try {
			Files.copy(in, Paths.get(fileTo.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			newPath = fileTo.getAbsolutePath();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newPath;
	}

	public static String verificaEcreaPathXlogo() {

		Path pathXimg = Paths.get(new File("").getAbsolutePath() + "/img/logo");

		File fileLogo = new File(pathXimg + "/logo.png");
		File logoTmp = new File(pathXimg + "/logo.tmp");

//		String linkLogo = "https://i.ibb.co/c6GGm20/logo.png";
		String linkLogo = ConfigIni.OnlineLinkLogo();

		BufferedInputStream in = null;

		try {
			Files.createDirectories(pathXimg);

			in = new BufferedInputStream(new URL(linkLogo).openStream());

			Files.copy(in, Paths.get(pathXimg + "/logo.tmp"), StandardCopyOption.REPLACE_EXISTING);

			if (!fileLogo.exists() || fileLogo.length() != logoTmp.length()) {
				Files.copy(Paths.get(logoTmp.getPath()), Paths.get(fileLogo.getPath()),
						StandardCopyOption.REPLACE_EXISTING);

			}

			Files.delete(Paths.get(logoTmp.getPath()));

		} catch (NoSuchFileException e) {
//			NON SCARICA IL FILE IN TEMPO MA TUTTO OKAY
//			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println(fileTo.length());
//		System.out.println(logoTmp.length());
//		System.out.println(fileLogo.exists());

		String newPath = fileLogo.getPath();

		return newPath;
	}

	private static int getLength(BufferedInputStream in) {
		int sizenew = 0;
		try {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {

				bos.write(buf, 0, len);
			}
			byte[] b = bos.toByteArray();
			sizenew = b.length;
		} catch (IOException e) {
			System.err.println(e);
		}
		return sizenew;

	}

}
