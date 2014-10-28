binSize = 32;

frameDat.xVal = buffer(mDat.xVal, binSize);
frameDat.yVal = buffer(mDat.yVal, binSize);
frameDat.zVal = buffer(mDat.zVal, binSize);

[s1, s2] = size(frameDat.xVal);

for i = 1:s2
    binDat.xVal = frameDat.xVal(:, i);
    binDat.yVal = frameDat.yVal(:, i);
    binDat.zVal = frameDat.zVal(:, i);
    
    binDat = binRemoveDC(binDat);
    
    binDatE = binEnergy(binDat);
    binDatV = binVariance(binDat);
    binDatA = binAverage(binDat);
    
    frameDat.pwr(i,1) = binDatE.xVal;
    frameDat.pwr(i,2) = binDatE.yVal;
    frameDat.pwr(i,3) = binDatE.zVal;
    
    
end

options = statset('Display','final');
gm = gmdistribution.fit([frameDat.pwr(:,1) frameDat.pwr(:,2) frameDat.pwr(:,3)], 2, 'Options',options);

% Simulate live run
for i = 1:s2
    binDat.xVal = frameDat.xVal(:, i);
    binDat.yVal = frameDat.yVal(:, i);
    binDat.zVal = frameDat.zVal(:, i);
    
    binDat = binRemoveDC(binDat);
    
    binDatE = binEnergy(binDat);
    binDatV = binVariance(binDat);
    binDatA = binAverage(binDat);
    
%     frameDat.pwr(i,1) = binDatE.xVal;
%     frameDat.pwr(i,2) = binDatE.yVal;
%     frameDat.pwr(i,3) = binDatE.zVal;
%     

    binDatGM = binGauss(binDatE, gm);
    frameDat.gmCluster(i) = binDatGM.idx;
    frameDat.gmP(i, :) = binDatGM.p;
end

figure;
[s1, s2] = size(frameDat.gmP);
numPlots = 2 + s2;
subplot(numPlots,1,1); plot(mDat.xVal); title('xVal'); axis([0 numel(mDat.xVal) min(mDat.xVal)*1.1 max(mDat.xVal)*1.1]);
subplot(numPlots,1,2); stem(1:binSize:length(frameDat.gmCluster)*binSize, frameDat.gmCluster-1); title('Clusters');


for i = 1:s2
    subplot(numPlots,1,i+2); stem(1:binSize:length(frameDat.gmCluster)*binSize, frameDat.gmP(:,i)); title(strcat('Posterior Probability Cluster_', int2str(i)))
end
%subplot(4,1,4); stem(1:binSize:length(frameDat.gmCluster)*binSize, frameDat.gmP(:,2));
