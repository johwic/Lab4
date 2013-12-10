P = load('particle_data.txt');
stepCount = size(P,1);

%% animate

for i = 1:stepCount
    plot( P(i,2:2:end), P(i,3:2:end),'*' );
    axis([0 500 0 500]);
    axis ij;
    %drawnow; 
    pause(.1);
end

%% stats

