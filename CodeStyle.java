public interface CodeStyle {
    boolean isEmptyLine(String string);

    boolean isImportLine(String string);

    boolean isHeaderLine(String string);

    Comment[] comments();
}