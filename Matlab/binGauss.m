function [ outBin ] = binGauss( inBin, gm)
%BINGAUSS classify using Gaussian Mixtures
%   Classify a single window

idx = cluster(gm, [inBin.xVal inBin.yVal, inBin.zVal]);
p = posterior(gm, [inBin.xVal inBin.yVal inBin.zVal]);
outBin.idx = idx;
outBin.p = p;
outBin.binFeature = inBin.binFeature;
outBin.classType = 'GaussianMix';

end

