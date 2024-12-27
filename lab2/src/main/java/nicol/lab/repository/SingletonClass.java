package nicol.lab.repository;

public final class SingletonClass {
    private static final class InstanceHolder {
        private static final SingletonClass INSTANCE = new SingletonClass();
    }

    public static SingletonClass getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private SingletonClass() {
    }
}
