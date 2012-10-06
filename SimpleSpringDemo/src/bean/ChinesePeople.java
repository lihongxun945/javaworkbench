package bean;

public class ChinesePeople implements People{
	private Vehicle vehicle = null;
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		this.vehicle.move();
	}

}
