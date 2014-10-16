function [xDat, yDat, zDat] = csv2xyz(filename)

	dat = csvread(filename);
	xDat = dat(:,1);
	yDat = dat(:,2);
	zDat = dat(:,3);

end
