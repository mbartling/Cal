function [xMasked, yMasked, zMasked] = quickMaskState(xdat, ydat, zdat, state, stateNum)

	mask = state == stateNum;

	xMasked = xdat(mask);
	yMasked = ydat(mask);
	zMasked = zdat(mask);

end
