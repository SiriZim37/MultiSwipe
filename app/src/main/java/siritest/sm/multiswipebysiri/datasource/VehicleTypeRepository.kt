package siritest.sm.multiswipebysiri.datasource

import siritest.sm.multiswipebysiri.data.model.Vehicle
import siritest.sm.multiswipebysiri.datasource.base.BaseRepositoryManagement
import java.util.*

class VehicleTypeRepository : BaseRepositoryManagement<Vehicle>() {

    private val adjectives: List<String> = arrayListOf(
        "Honda",
        "Yamaha",
        "Toyota",
        "Suzuki"
    )

    private val names: List<String> = arrayListOf(
        "Yaris",
        "YarisX1",
        "YarisX2",
        "CamryX1",
        "CamryX2",
        "CamryX3",
        "AltisX1",
        "AltisX2",
        "AltisX3"
    )

    override fun generateNewItem(): Vehicle {

        val vehicleType = generateVehicleName()
        val vehicleName = generateVehicleName()
        val vehicleLocation = ""
        val vehiclePrice = generateVehiclePrice()

        val vehicle = Vehicle(vehicleType, vehicleName , vehicleLocation, vehiclePrice)

        addItem(vehicle)

        return vehicle
    }


    private fun generateVehicleName() =
        "${adjectives.shuffled().take(1)[0]} ${names.shuffled().take(1)[0]}"

    private fun generateVehiclePrice() : Double  = ((80..500).random().toFloat() / 100f).toDouble()

    private fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) + start

    companion object {
        private var instance: VehicleTypeRepository? = null

        fun getInstance(): VehicleTypeRepository {
            if (instance == null)
                instance =
                    VehicleTypeRepository()

            return instance as VehicleTypeRepository
        }
    }
}