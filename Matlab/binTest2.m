binSize = 32;
clear frameDat
frameDat.xVal = buffer(mDat.xVal, binSize);
frameDat.yVal = buffer(mDat.yVal, binSize);
frameDat.zVal = buffer(mDat.zVal, binSize);

[s1, s2] = size(frameDat.xVal);

% Simulate live run
for i = 1:s2
    binDat.xVal = frameDat.xVal(:, i);
    binDat.yVal = frameDat.yVal(:, i);
    binDat.zVal = frameDat.zVal(:, i);
    
    binDat = binRemoveDC(binDat);
    
    binDatE = binEnergy(binDat);
    binDatV = binVariance(binDat);
    binDatA = binAverage(binDat);
    
    binDatGM = binGauss(binDatE, gm);
    frameDat.gmCluster(i) = binDatGM.idx;
    frameDat.gmP(i, :) = binDatGM.p;
end

figure;
[s1, s2] = size(frameDat.gmP);
numPlots = 2 + s2;
subplot(numPlots,1,1); plot(mDat.xVal); title('xVal'); 
axis([0 numel(mDat.xVal) min(mDat.xVal)*1.1 max(mDat.xVal)*1.1]);
subplot(numPlots,1,2); stem(1:binSize:length(frameDat.gmCluster)*binSize, frameDat.gmCluster-1); title('Clusters');
axis([0 numel(mDat.xVal) 0 2]);

for i = 1:s2
    subplot(numPlots,1,i+2); stem(1:binSize:length(frameDat.gmCluster)*binSize, frameDat.gmP(:,i)); title(strcat('Posterior Probability Cluster_', int2str(i)))
    axis([0 numel(mDat.xVal) 0 2]);
end
%subplot(4,1,4); stem(1:binSize:length(frameDat.gmCluster)*binSize, frameDat.gmP(:,2));
