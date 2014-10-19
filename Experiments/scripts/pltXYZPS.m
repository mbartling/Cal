
figure;
subplot(5,1,1); plot(xDat); xlabel('time'); title('X');
subplot(5,1,2); plot(yDat); xlabel('time'); title('Y');
subplot(5,1,3); plot(zDat); xlabel('time'); title('Z');
subplot(5,1,5); stem(prox); xlabel('time'); title('Proximity');
subplot(5,1,4); stem(state); xlabel('time'); title('States');
