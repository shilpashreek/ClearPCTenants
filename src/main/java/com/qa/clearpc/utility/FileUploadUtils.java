
package com.qa.clearpc.utility;

import java.io.File;

import com.jacob.com.LibraryLoader;
import com.qa.clearpc.BaseClass.BaseClass;

import autoitx4java.AutoItX;

/**
 * @author ShilpaShree
 *
 */
public class FileUploadUtils extends BaseClass {
	
	static String titleOfWindowOpened="";
	
	
	private static void setJACOB_DLL_PATH()
	{
		try {
		String workingDir = System.getProperty("user.dir");
		final String JACOB_DLL_TO_USE =
		        System.getProperty("sun.arch.data.model").contains("32")
		                ? "jacob-1.18-x64.dll"
		                : "jacob-1.18-x64.dll";
		String jacobdllpath = workingDir + "\\AutoIT\\"+ JACOB_DLL_TO_USE;
		File jacobFile=new File(jacobdllpath);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobFile.getAbsolutePath());
		}catch(Exception e)
		{
			log.error(e.getMessage());
		}
	}

	/* 
	 * @param WindowTitle
	 * @param filepath
	 * @param ControlID
	 */
	public static boolean uploadAsset(String WindowTitle, String filepath, String controlId) {
		boolean status = false;
		try {
			setJACOB_DLL_PATH();
			AutoItX uploadWindow = new AutoItX();
			if (WaituntilWindowExists(WindowTitle, uploadWindow, controlId)) {
				uploadWindow.sleep(100);
				uploadWindow.ControlSetText(titleOfWindowOpened, "", controlId, filepath);
				log.info("\"" + filepath + "\" is passed in the input box in the window with title :: \"" + WindowTitle
						+ "\"");
				Thread.sleep(1000);
				uploadWindow.controlClick(titleOfWindowOpened, "", "&Open");
				status = true;
			} else {
				log.info("Required window \"" + WindowTitle + "\" is not opened");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		log.info(status);
		return status;
	}

	// To wait until window opens
	/**
	 * @param WindowTitle
	 * @param uploadWindow
	 * @param controlid
	 * @return
	 */
	private static boolean WaituntilWindowExists(String WindowTitle, AutoItX uploadWindow, String controlid) {
		boolean flag = false;
		try {
			WaitUntilWindowExists: for (int i = 1; i <= 180; i++) {
				for (String window : WindowTitle.split("&&")) {
					if (uploadWindow.winExists(window)) {
						log.info("Required Window :: \"" + window + "\" is opened");
						flag = true;
						// To maximize the window
						uploadWindow.winSetState(window, "", AutoItX.SW_SHOWMAXIMIZED);
						boolean focus = uploadWindow.controlFocus(window, "", controlid);
						log.info("focus on the window :: \"" + window + "\" is " + focus);
						titleOfWindowOpened = window;
						break WaitUntilWindowExists;
					}
				}
				if (!flag) {
					Thread.sleep(1000);
					log.info("Required windows :: \"" + WindowTitle + "\" are not opened in " + i + " seconds");
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return flag;
	}

	// To wait until window opens
	/**
	 * @param WindowTitle
	 * @return
	 */
	public static boolean checkIfWindowExists(String WindowTitle) {
		boolean flag = false;
		try {
			setJACOB_DLL_PATH();
			AutoItX uploadWindow = new AutoItX();
			WaitUntilWindowExists: for (int i = 1; i <= 30; i++) {
				for (String window : WindowTitle.split("&&")) {
					if (uploadWindow.winExists(window)) {
						log.info("Required Window :: \"" + window + "\" is opened");
						flag = true;
						break WaitUntilWindowExists;
					}
				}
				if (!flag) {
					Thread.sleep(1000);
					log.info("Required windows :: \"" + WindowTitle + "\" are not opened in " + i + " seconds");
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());

		}
		return flag;
	}

	
	/**
	 * @param WindowTitle
	 * @return
	 */
	public static boolean checkIfWindowExistsNow(String WindowTitle) {
		boolean flag = false;
		try {
			setJACOB_DLL_PATH();
			AutoItX uploadWindow = new AutoItX();
			WaitUntilWindowExists: for (int i = 1; i <= 4; i++) {
				for (String window : WindowTitle.split("&&")) {
					if (uploadWindow.winExists(window)) {
						log.info("Required Window :: \"" + window + "\" is opened");
						flag = true;
						break WaitUntilWindowExists;
					}
				}
				if (!flag) {
					Thread.sleep(1000);
					log.info("Required windows :: \"" + WindowTitle + "\" are not opened in " + i + " seconds");
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());

		}
		return flag;
	}

	
	/**
	 * @param WindowTitle
	 * @return
	 */
	public static boolean Close(String WindowTitle) {
		boolean flag = false;
		try {
			setJACOB_DLL_PATH();
			AutoItX uploadWindow = new AutoItX();
			for (String window : WindowTitle.split("&&")) {
				if (uploadWindow.winExists(window)) {
					log.info("Required Window :: \"" + WindowTitle + "\" is opened");
					uploadWindow.winClose(WindowTitle);
					flag = true;
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return flag;
	}

	
	/**
	 * @param windowTitle
	 * @param controlID
	 */
	public static void click(String windowTitle, String controlID) {
		setJACOB_DLL_PATH();
		AutoItX uploadWindow = new AutoItX();
		if (WaituntilWindowExists(windowTitle, uploadWindow, controlID)) {
			uploadWindow.sleep(100);
			uploadWindow.controlClick(titleOfWindowOpened, "", controlID);
		} else {
			log.info("Required window \"" + windowTitle + "\" is not opened");
		}
	}
	
	/**
	 * @param windowTitle
	 */
	public static void clickOnOpen(String windowTitle) {
		setJACOB_DLL_PATH();
		AutoItX uploadWindow = new AutoItX();
		if (WaituntilWindowExists(windowTitle, uploadWindow, "&Open")) {
			uploadWindow.sleep(100);
			uploadWindow.controlClick(titleOfWindowOpened, "", "&Open");
		} else {
			log.info("Required window \"" + windowTitle + "\" is not opened");
		}
		uploadWindow.winSetState(windowTitle, "", AutoItX.SW_SHOWMINIMIZED);
	}

	/**
	 * @param windowTitle
	 * @param controlID
	 * @param text
	 */
	public static void setText(String windowTitle, String controlID, String text) {
		setJACOB_DLL_PATH();
		AutoItX uploadWindow = new AutoItX();
		if (WaituntilWindowExists(windowTitle, uploadWindow, controlID)) {
			uploadWindow.sleep(100);
			uploadWindow.ControlSetText(titleOfWindowOpened, "", controlID, text);
		} else {
			log.info("Required window \"" + windowTitle + "\" is not opened");
		}
	}

	/**
	 * @param windowTitle
	 * @param controlId
	 * @param text
	 */
	public static void sendKeyStroke(String windowTitle, String controlId, String text) throws InterruptedException {
		setJACOB_DLL_PATH();
		AutoItX uploadWindow = new AutoItX();
		if (WaituntilWindowExists(windowTitle, uploadWindow, "")) {
			setFocusOnWindow(titleOfWindowOpened,controlId);
			uploadWindow.controlFocus(titleOfWindowOpened, "", controlId);
			uploadWindow.winActivate(titleOfWindowOpened);
			uploadWindow.winWaitActive(titleOfWindowOpened);
			uploadWindow.send(text,false);
		} else {
			log.info("Required window \"" + windowTitle + "\" is not opened");
		}
	}

	/**
	 * @param windowTitle
	 * @param controlId
	 * 
	 */
	public static void setFocusOnWindow(String windowTitle, String controlId) throws InterruptedException {
		setJACOB_DLL_PATH();
		AutoItX uploadWindow = new AutoItX();
		uploadWindow.winSetState(windowTitle, "", AutoItX.SW_SHOWMAXIMIZED);
		uploadWindow.controlFocus(windowTitle, "", controlId);
	}



	
	
	
}
