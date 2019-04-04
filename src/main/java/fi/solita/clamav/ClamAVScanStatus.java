package fi.solita.clamav;

/**
 *
 * @author ashutoshmimani
 */
public class ClamAVScanStatus
{

    private boolean status;

    ClamAVScanStatus(boolean status)
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
