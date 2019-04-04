package fi.solita.clamav;

/**
 *
 * @author ashutoshmimani
 */
public class StatusResponse
{

    private boolean status;

    StatusResponse(boolean status)
    {
        this.status = status;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }
}
