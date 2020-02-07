package cs2030.simulator;

import java.util.Random;



































class RandomGenerator
{
  private Random rngArrival;
  private Random rngService;
  private Random rngRest;
  private Random rngRestPeriod;
  private Random rngWait;
  private Random rngCustomerType;
  private final double customerArrivalRate;
  private final double customerServiceRate;
  private final double serverRestingRate;
  
  RandomGenerator(int paramInt, double paramDouble1, double paramDouble2)
  {
    rngArrival = new Random(paramInt);
    rngService = new Random(paramInt + 1);
    customerArrivalRate = paramDouble1;
    customerServiceRate = paramDouble2;
    serverRestingRate = 0.0D;
  }
  







  RandomGenerator(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    rngArrival = new Random(paramInt);
    rngService = new Random(paramInt + 1);
    rngRest = new Random(paramInt + 2);
    rngRestPeriod = new Random(paramInt + 3);
    rngCustomerType = new Random(paramInt + 4);
    
    customerArrivalRate = paramDouble1;
    customerServiceRate = paramDouble2;
    serverRestingRate = paramDouble3;
  }
  






  double genInterArrivalTime()
  {
    return -Math.log(rngArrival.nextDouble()) / customerArrivalRate;
  }
  






  double genServiceTime()
  {
    return -Math.log(rngService.nextDouble()) / customerServiceRate;
  }
  






  double genRandomRest()
  {
    return rngRest.nextDouble();
  }
  






  double genRestPeriod()
  {
    return -Math.log(rngRestPeriod.nextDouble()) / serverRestingRate;
  }
  




  double genCustomerType()
  {
    return rngCustomerType.nextDouble();
  }
}
