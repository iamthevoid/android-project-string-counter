public enum Comment {
    slashslash("//"),
    slashstar("/*") {

        @Override
        public boolean endsIn(String string) {
            return string.contains("*/");
        }
    },
    javadoc("/**") {
        @Override
        public boolean endsIn(String string) {
            return string.contains("*/");
        }
    },
    xml("<!--") {
        @Override
        public boolean endsIn(String string) {
            return string.contains("-->");
        }
    };

    Comment(String commentStart) {
        this.mCommentStart = commentStart;
    }

    String mCommentStart;

    public String getCommentStart() {
        return mCommentStart;
    }

    public boolean inString(String string) {
        return string.trim().startsWith(mCommentStart);
    }

    public boolean endsIn(String string) {
        return true;
    }
}