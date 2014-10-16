function plotRaw(xDat,yDat,zDat)

	figure;
	subplot(3,1,1); plot(xDat); title('x');
	subplot(3,1,2); plot(yDat); title('y');
	subplot(3,1,3); plot(zDat); title('z');
end
