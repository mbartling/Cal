function plotRaw(xDat,yDat,zDat)

	figure;
	subplot(3,1,1); stem(xDat); title('x');
	subplot(3,1,2); stem(yDat); title('y');
	subplot(3,1,3); stem(zDat); title('z');
end
