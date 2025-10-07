import React, { useEffect, useState } from 'react';
import { getActivities } from '../service/api';
import { useNavigate } from 'react-router-dom'; // 👈 Import useNavigate

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate(); // 👈 Init navigate

  useEffect(() => {
    const fetchActivities = async () => {
      try {
        const response = await getActivities();
        setActivities(response.data);
      } catch (error) {
        console.error('Error fetching activities:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchActivities();
  }, []);

  if (loading) {
    return <div className="text-center text-lg text-gray-600 mt-10">Loading activities...</div>;
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-6 text-center">Your Activities</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        {activities.map((activity) => (
          <div
            key={activity.id}
            className="bg-white shadow-lg rounded-lg p-5 border border-gray-200 hover:shadow-xl transition-shadow duration-300 cursor-pointer"
            onClick={() => navigate(`/activities/${activity.id}`, { state: { activity } })}// 👈 Navigate to detail page
          >
            <h3 className="text-xl font-semibold mb-3 text-indigo-600">{activity.type}</h3>
            <p className="text-gray-700 mb-2">
              <span className="font-medium">Calories Burned:</span> {activity.caloriesBurn} kcal
            </p>
            <p className="text-gray-700">
              <span className="font-medium">Duration:</span> {activity.duration} min
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ActivityList;
