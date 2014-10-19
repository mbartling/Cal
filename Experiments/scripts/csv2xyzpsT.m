function [xDat, yDat, zDat, prox, states, takingData] = csv2xyzpsT(filename)

	dat = csvread(filename);
	xDat = dat(:,1);
	yDat = dat(:,2);
	zDat = dat(:,3);
	prox = dat(:,4);
	states = dat(:,5);
	takingData = dat(:,6);

end
