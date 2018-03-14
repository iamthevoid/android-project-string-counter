public enum SourceType implements CodeStyle {
    java {
        @Override
        public boolean isImportLine(String string) {
            return string.startsWith("import");
        }

        @Override
        public boolean isHeaderLine(String string) {
            return string.startsWith("package");
        }

        @Override
        public Comment[] comments() {
            return new Comment[]{Comment.slashslash, Comment.slashstar, Comment.javadoc};
        }
    },
    src {
        @Override
        public boolean isImportLine(String string) {
            return string.startsWith("import");
        }

        @Override
        public boolean isHeaderLine(String string) {
            return string.startsWith("package");
        }

        @Override
        public Comment[] comments() {
            return new Comment[]{Comment.slashslash, Comment.slashstar, Comment.javadoc};
        }
    },
    res {
        @Override
        public boolean isImportLine(String string) {
            return false;
        }

        @Override
        public boolean isHeaderLine(String string) {
            return string.startsWith("<?xml");
        }

        @Override
        public Comment[] comments() {
            return new Comment[]{Comment.xml};
        }
    };

    @Override
    public boolean isEmptyLine(String string) {
        return string.isEmpty();
    }
}