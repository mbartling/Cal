function [xndc, yndc, zndc] = rmdc(xDat, yDat, zDat)

	xfft = fft(xDat);
	yfft = fft(yDat);
	zfft = fft(zDat);

	xfft(1) = 0;
	yfft(1) = 0;
	zfft(1) = 0;

	xndc = real(ifft(xfft));
	yndc = real(ifft(yfft));
	zndc = real(ifft(zfft));

end
