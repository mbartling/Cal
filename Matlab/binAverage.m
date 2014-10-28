function [ outBin ] = binAverage( inBin )
%BINENERGY Calculate simple window magnitude Average 
%   Assume inBin is of complex FFT type signal to magnitude

if (strcmp('FFT', inBin.xValType))
    outBin.xVal = mean(abs(inBin.xVal));
    outBin.xValType = 'Average';
else
    error('binAverage:valType', 'Value Type is not supported');
end
if (strcmp('FFT', inBin.yValType))
    outBin.yVal = mean(abs(inBin.yVal));
    outBin.yValType = 'Average';
else
    error('binAverage:valType', 'Value Type is not supported');
end
if (strcmp('FFT', inBin.zValType))
    outBin.zVal = mean(abs(inBin.zVal));
    outBin.zValType = 'Average';
else
    error('binAverage:valType', 'Value Type is not supported');
end
outBin.binFeature = 'Average';

end
