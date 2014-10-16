function [xDat, yDat, zDat, states, text] = csv2xyzst(filename)

	dat = csvread(filename);
	xDat = dat(:,1);
	yDat = dat(:,2);
	zDat = dat(:,3);
	states = dat(:,4);
	text = dat(:,5);

end
