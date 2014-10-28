function [ outBin ] = binEnergy( inBin )
%BINENERGY Calculate simple window energy with L2 norm
%   Assume inBin is of complex FFT type signal

if (strcmp('FFT', inBin.xValType))
    outBin.xVal = sum(abs(inBin.xVal))/numel(inBin.xVal);
    outBin.xValType = 'Energy';
else
    error('binEnergy:valType', 'Value Type is not supported');
end
if (strcmp('FFT', inBin.yValType))
    outBin.yVal = sum(abs(inBin.yVal))/numel(inBin.yVal);
    outBin.yValType = 'Energy';
else
    error('binEnergy:valType', 'Value Type is not supported');
end
if (strcmp('FFT', inBin.zValType))
    outBin.zVal = sum(abs(inBin.zVal))/numel(inBin.zVal);
    outBin.zValType = 'Energy';
else
    error('binEnergy:valType', 'Value Type is not supported');
end
outBin.binFeature = 'Energy';

end

