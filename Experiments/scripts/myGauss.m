function gx = myGauss(n, sigma)
	% zero mean

	num = linspace(-n/2,n/2,n);
	gx = (1/(sqrt(2*pi)*sigma))*exp(-(num.^2)/(2*sigma^2));
end
