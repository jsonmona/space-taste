package cf.spacetaste.app.network;

public interface AsyncResultPromise<T> {
    void onResult(boolean success, T result);
}
