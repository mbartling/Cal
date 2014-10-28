function [ outBin ] = binRemoveDC( inBin )
%BINREMOVEDC take FFT and remove DC component of inBin
%   I assume the window size is a power of 2

outBin.xVal = fft(inBin.xVal);
outBin.xVal(1) = 0; % Remove DC component
outBin.xValType = 'FFT';

outBin.yVal = fft(inBin.yVal);
outBin.yVal(1) = 0; % Remove DC component
outBin.yValType = 'FFT';

outBin.zVal = fft(inBin.zVal);
outBin.zVal(1) = 0; % Remove DC component
outBin.zValType = 'FFT';


end

