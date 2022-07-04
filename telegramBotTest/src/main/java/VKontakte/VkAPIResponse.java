package VKontakte;

public class VkAPIResponse<T> {
    private  T data;
    private  boolean error;

    public VkAPIResponse(T data) {
        this.data = data;
        error = false;
    }

    public VkAPIResponse(T data, boolean isError) {
        this.data = data;
        error = isError;
    }

    public T getData() { return data; }

    public boolean isSuccess() { return error; }
}
