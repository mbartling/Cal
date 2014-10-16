
xfft = fft(xdat);
yfft = fft(ydat);
zfft = fft(zdat);

xfft(1) = 0;
yfft(1) = 0;
zfft(1) = 0;

xndc = real(ifft(xfft));
yndc = real(ifft(yfft));
zndc = real(ifft(zfft));

maskI = state == 0;
maskW = state == 1;
maskS = state == 2;
maskT = state == 3;

xdatI = xndc(maskI);
ydatI = yndc(maskI);
zdatI = zndc(maskI);

xdatW = xndc(maskW);
ydatW = yndc(maskW);
zdatW = zndc(maskW);

xdatS = xndc(maskS);
ydatS = yndc(maskS);
zdatS = zndc(maskS);

xdatT = xndc(maskT);
ydatT = yndc(maskT);
zdatT = zndc(maskT);


figure;

hold on
quiver3(xdatI, ydatI, zdatI, gradient(xdatI), gradient(ydatI), gradient(zdatI), 'r');
quiver3(xdatW, ydatW, zdatW, gradient(xdatW), gradient(ydatW), gradient(zdatW), 'g');
quiver3(xdatS, ydatS, zdatS, gradient(xdatS), gradient(ydatS), gradient(zdatS), 'b');
quiver3(xdatT, ydatT, zdatT, gradient(xdatT), gradient(ydatT), gradient(zdatT), 'c');
legend('Idle = red','Walking = green'	,'Standing = blue','Typing = cyan');
hold off

xlabel('x');
ylabel('y');
zlabel('z');
set(gca, "color", [0.6 0.6 0.6]);
grid on
