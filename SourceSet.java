
import java.io.*;
import java.util.*;

class SourceSet {

	private SourceType mSourceType;
	private List<String> mFolders;
	private String mSrcPath;
	private int mFilesCount;
	private int mSummaryLines;
	private int mEmptyLinesCount;
	private int mLinesCount;
	private int mCommentsLinesCount;
	private int mHeaderLinesCount;
	private int mImportLinesCount;

	public SourceSet(SourceType sourceType, List<String> folders, String srcPath) {
		this.mFolders = folders;
		this.mSrcPath = srcPath;
		this.mSourceType = sourceType;
		count();
	}

	private void count() {
		for (String folderName : mFolders) {
			File sourceDir = new File(mSrcPath, folderName + "/" + mSourceType.name());
			if (!sourceDir.exists()) continue;
			countRecursively(sourceDir);
		}
	}

	private void countRecursively(File file) {
		if (file.isDirectory()) {
			for (String filename : file.list()) {
				countRecursively(new File(file, filename));
			}
		} else {
			mFilesCount++;
			try {
				BufferedReader reader = new BufferedReader(new FileReader((file.getPath())));
				String string;

				Comment[] comments = mSourceType.comments();

				top: while ((string = reader.readLine()) != null) {

					mSummaryLines++;
					
					if (mSourceType.isEmptyLine(string)) {
						mEmptyLinesCount++;
					} else if (mSourceType.isImportLine(string)) {
						mImportLinesCount++;
					} else if (mSourceType.isHeaderLine(string)) {
						mHeaderLinesCount++;
					} else {
						for (Comment c : comments) {
							if (c.inString(string)) {
								if (c.endsIn(string)) {
									mCommentsLinesCount++;
									continue top;
								} 

								do {
									mCommentsLinesCount++;
									string = reader.readLine();
								} while (!c.endsIn(string));

								mCommentsLinesCount++;
								
								continue top;
							}
						}

						mLinesCount++;
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		return String.format("\ndir: %s:\n" + 
			"---in flavours: %s\n" + 
			"---lines: %d\n" + 
			"---code lines: %d\n" + 
			"---comment lines: %d\n" + 
			"---empty lines: %d\n" + 
			"---header lines(package, <xml? and so): %d\n" +
			"---import lines: %d\n" + 
			"---files count = %d\n", 
			new File(mSrcPath, mSourceType.name()).getAbsolutePath(), 
			Arrays.toString(mFolders.toArray()), 
			mSummaryLines,
			mLinesCount,
			mCommentsLinesCount,
			mEmptyLinesCount,
			mHeaderLinesCount,
			mImportLinesCount,
			mFilesCount);
	}
}





