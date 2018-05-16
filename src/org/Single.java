package org;

import ptolemy.actor.TypedIOPort;
import ptolemy.actor.lib.LimitedFiringSource;
import ptolemy.data.DoubleToken;
import ptolemy.data.StringToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class Single extends LimitedFiringSource {

	public TypedIOPort mu;
	public TypedIOPort lamda;
	public TypedIOPort etat;
	public TypedIOPort outK;
	
	
	
	
	public Single(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
		super(container, name);
		// TODO Auto-generated constructor stub
		
		_firingCountLimit=1;

		mu = new TypedIOPort(this, "Mu", true, false);
		lamda = new TypedIOPort(this, "lambda", true, false);
		etat = new TypedIOPort(this, "etat", true, false);
		
		etat.setTypeEquals(BaseType.INT);
		outK = new TypedIOPort(this, "outK", false, true);
		

		mu.setTypeEquals(BaseType.DOUBLE);
		//output.setTypeEquals(BaseType.DOUBLE);
		lamda.setTypeEquals(BaseType.DOUBLE);
		outK.setTypeEquals(BaseType.DOUBLE);

	}

	@Override
	public void fire() throws IllegalActionException {
		// TODO Auto-generated method stub
		super.fire();
		
		double lambda=Double.valueOf((lamda.get(0)).toString());
		double Mu=Double.valueOf((mu.get(0)).toString());
		int k = Integer.valueOf((etat.get(0)).toString());
		double P = lambda/Mu; 
		double P0= 1 - (P);
		double Pi = (1-(P)* (Math.pow(P, k)));
		double Es= P/(1-P);
		double VARs = P/(Math.pow(P, 2));
		double Prob=0;
		if(k==0) Prob = 1 - (Math.pow(P,2));
		else Prob = (1-P)*(Math.pow(P,k+1));
		double Eq= (Math.pow(P,2))/(1-P);
		double VARq=(P*P) * (1+P-(P*P))/(Math.pow((1-P), 2));
		//double Frt(r)=1-Math.exp(-r*Mu*(1-P));
		double MResponseTime=(1/Mu)/(1-P);
		double VARresponseTime=(1/(Mu*Mu))/(Math.pow((1-P),2));
		//double qPercentileRT= MResponseTime* Math.log((100/(100-q)));
		//double NPercentileRT= 2.3 * MResponseTime;
		//double Fwt(w)= 1-(P* Math.exp(-Mu*w*(1-P));
		double MeanWT=P*(1/Mu)/(1-P);
		double VARwt=(2-P)*P/((Mu*Mu)*(Math.pow((1-P),2)));
		//double qPercentileWT= (MeanWT/P) * Math.log(100*P/(100-q));
		//if(qPercentileWT<0) qPercentileWT=0;
		//double NPercentileWT=(MeanWT/P) * Math.log(10*P);
		//if(NPercentileWT<0) NPercentileWT=0;
		double Pfinding=(Math.pow(P,k));
		
		outK.send(0, new DoubleToken(VARs));
		
	}

}
