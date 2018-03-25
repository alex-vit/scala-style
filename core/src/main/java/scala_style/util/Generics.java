package scala_style.util;

public final class Generics {

    private Generics() {
    }

    public static <Super, Sub> boolean isSuperType(Class<Super> superType, Class<Sub> subType) {
        return superType.isAssignableFrom(subType);
    }

}
