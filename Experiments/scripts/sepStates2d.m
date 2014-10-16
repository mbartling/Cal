
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
subplot(3,1,1);
hold on
scatter(xdatW, ydatW, 'g');
scatter(xdatI, ydatI, 'r');
scatter(xdatS, ydatS, 'b');
scatter(xdatT, ydatT, 'c');
xlabel('x');
ylabel('y');
set(gca, "color", [0.6 0.6 0.6]);
hold off
grid on
legend('Idle = red','Walking = green'	,'Standing = blue','Typing = cyan');

subplot(3,1,2);
hold on
scatter(ydatW, zdatW, 'g');
scatter(ydatI, zdatI, 'r');
scatter(ydatS, zdatS, 'b');
scatter(ydatT, zdatT, 'c');
xlabel('y');
ylabel('z');
set(gca, "color", [0.6 0.6 0.6]);
hold off
grid on
legend('Idle = red','Walking = green'	,'Standing = blue','Typing = cyan');

subplot(3,1,3);
hold on
scatter(xdatW, zdatW, 'g');
scatter(xdatI, zdatI, 'r');
scatter(xdatS, zdatS, 'b');
scatter(xdatT, zdatT, 'c');
xlabel('x');
ylabel('z');
hold off
legend('Idle = red','Walking = green'	,'Standing = blue','Typing = cyan');
%zlabel('z');
set(gca, "color", [0.6 0.6 0.6]);
grid on
