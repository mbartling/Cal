function [ outBin ] = binVariance( inBin )
%BINENERGY Calculate simple window magnitude variance 
%   Assume inBin is of complex FFT type signal to magnitude

if (strcmp('FFT', inBin.xValType))
    outBin.xVal = var(abs(inBin.xVal));
    outBin.xValType = 'Variance';
else
    error('binVariance:valType', 'Value Type is not supported');
end
if (strcmp('FFT', inBin.yValType))
    outBin.yVal = var(abs(inBin.yVal));
    outBin.yValType = 'Variance';
else
    error('binVariance:valType', 'Value Type is not supported');
end
if (strcmp('FFT', inBin.zValType))
    outBin.zVal = var(abs(inBin.zVal));
    outBin.zValType = 'Variance';
else
    error('binVariance:valType', 'Value Type is not supported');
end
outBin.binFeature = 'Variance';

end
