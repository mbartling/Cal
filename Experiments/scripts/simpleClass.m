function theta = simpleClass(thetain, x, y, alpha)

	theta = thetain + alpha*sum(x.*(y-1./(1+exp(thetain'*x))),2);

end
