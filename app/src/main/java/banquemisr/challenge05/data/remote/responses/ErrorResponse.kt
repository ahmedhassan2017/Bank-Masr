package banquemisr.challenge05.data.remote.responses

import android.os.Parcel
import android.os.Parcelable

/**
 * Error response data holder.
 */
data class ErrorResponse(val message: String?, var errors: Map<String, List<String>> = emptyMap()): Parcelable
{
    constructor(parcel: Parcel) : this(parcel.readString(), TODO("errors"))
    {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(message)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ErrorResponse>
    {
        override fun createFromParcel(parcel: Parcel): ErrorResponse
        {
            return ErrorResponse(parcel)
        }

        override fun newArray(size: Int): Array<ErrorResponse?>
        {
            return arrayOfNulls(size)
        }
    }
}